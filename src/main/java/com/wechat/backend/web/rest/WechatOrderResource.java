package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.domain.WechatOrderItem;
import com.wechat.backend.service.WechatOrderItemService;
import com.wechat.backend.service.WechatOrderService;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.service.dto.WechatOrderItemDTO;
import com.wechat.backend.service.dto.WechatProductDTO;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import com.wechat.backend.service.dto.WechatOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WechatOrder.
 */
@RestController
@RequestMapping("/api")
public class WechatOrderResource {

    private final Logger log = LoggerFactory.getLogger(WechatOrderResource.class);

    private static final String ENTITY_NAME = "wechatOrder";

    private final WechatOrderService wechatOrderService;
    private final WechatOrderItemService wechatOrderItemService;
    private final WechatProductService wechatProductService;
    public WechatOrderResource(WechatOrderService wechatOrderService,WechatOrderItemService wechatOrderItemService,WechatProductService wechatProductService) {
        this.wechatOrderService = wechatOrderService;
        this.wechatOrderItemService=wechatOrderItemService;
        this.wechatProductService=wechatProductService;
    }

    /**
     * POST  /wechat-orders : Create a new wechatOrder.
     *
     * @param wechatOrderDTO the wechatOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatOrderDTO, or with status 400 (Bad Request) if the wechatOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-orders")
    @Timed
    public ResponseEntity<WechatOrderDTO> createWechatOrder(@Valid @RequestBody WechatOrderDTO wechatOrderDTO) throws URISyntaxException {
        log.debug("REST request to save WechatOrder : {}", wechatOrderDTO);
        if (wechatOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wechatOrderDTO.setStatus("0");
        WechatOrderDTO result = wechatOrderService.save(wechatOrderDTO);

        for(WechatOrderItemDTO wechatOrderItem:wechatOrderDTO.getWechatOrderItems()){
            WechatProductDTO wechatProductDTO=wechatProductService.findOne(wechatOrderItem.getProductId());
            if(wechatProductDTO.isSellOut()){
                throw new BadRequestAlertException("产品已售出", ENTITY_NAME, "产品已售出");
            }
            wechatProductDTO.setSellOut(Boolean.TRUE);
            wechatProductService.save(wechatProductDTO);
            wechatOrderItem.setWechatOrderId(result.getId());
            wechatOrderItemService.save(wechatOrderItem);
        }
        return ResponseEntity.created(new URI("/api/wechat-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-orders : Updates an existing wechatOrder.
     *
     * @param wechatOrderDTO the wechatOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatOrderDTO,
     * or with status 400 (Bad Request) if the wechatOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-orders")
    @Timed
    public ResponseEntity<WechatOrderDTO> updateWechatOrder(@Valid @RequestBody WechatOrderDTO wechatOrderDTO) throws URISyntaxException {
        log.debug("REST request to update WechatOrder : {}", wechatOrderDTO);
        if (wechatOrderDTO.getId() == null) {
            return createWechatOrder(wechatOrderDTO);
        }
        WechatOrderDTO result = wechatOrderService.save(wechatOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-orders : get all the wechatOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatOrders in body
     */
    @GetMapping("/wechat-orders")
    @Timed
    public ResponseEntity<List<WechatOrderDTO>> getAllWechatOrders(Pageable pageable) {
        log.debug("REST request to get a page of WechatOrders");
        Page<WechatOrderDTO> page = wechatOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/wechat-orders/mine/{userId}")
    @Timed
    public ResponseEntity<List<WechatOrderDTO>> getAllWechatOrdersByUser(Pageable pageable,@PathVariable Long userId) {
        log.debug("REST request to get a page of WechatOrders");


        Page<WechatOrderDTO> page = wechatOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /wechat-orders/:id : get the "id" wechatOrder.
     *
     * @param id the id of the wechatOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-orders/{id}")
    @Timed
    public ResponseEntity<WechatOrderDTO> getWechatOrder(@PathVariable Long id) {
        log.debug("REST request to get WechatOrder : {}", id);
        WechatOrderDTO wechatOrderDTO = wechatOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatOrderDTO));
    }

    /**
     * DELETE  /wechat-orders/:id : delete the "id" wechatOrder.
     *
     * @param id the id of the wechatOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatOrder(@PathVariable Long id) {
        log.debug("REST request to delete WechatOrder : {}", id);
        wechatOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
