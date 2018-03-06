package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.service.WechatOrderItemService;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import com.wechat.backend.service.dto.WechatOrderItemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WechatOrderItem.
 */
@RestController
@RequestMapping("/api")
public class WechatOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(WechatOrderItemResource.class);

    private static final String ENTITY_NAME = "wechatOrderItem";

    private final WechatOrderItemService wechatOrderItemService;

    public WechatOrderItemResource(WechatOrderItemService wechatOrderItemService) {
        this.wechatOrderItemService = wechatOrderItemService;
    }

    /**
     * POST  /wechat-order-items : Create a new wechatOrderItem.
     *
     * @param wechatOrderItemDTO the wechatOrderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatOrderItemDTO, or with status 400 (Bad Request) if the wechatOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-order-items")
    @Timed
    public ResponseEntity<WechatOrderItemDTO> createWechatOrderItem(@RequestBody WechatOrderItemDTO wechatOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save WechatOrderItem : {}", wechatOrderItemDTO);
        if (wechatOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WechatOrderItemDTO result = wechatOrderItemService.save(wechatOrderItemDTO);
        return ResponseEntity.created(new URI("/api/wechat-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-order-items : Updates an existing wechatOrderItem.
     *
     * @param wechatOrderItemDTO the wechatOrderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatOrderItemDTO,
     * or with status 400 (Bad Request) if the wechatOrderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatOrderItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-order-items")
    @Timed
    public ResponseEntity<WechatOrderItemDTO> updateWechatOrderItem(@RequestBody WechatOrderItemDTO wechatOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update WechatOrderItem : {}", wechatOrderItemDTO);
        if (wechatOrderItemDTO.getId() == null) {
            return createWechatOrderItem(wechatOrderItemDTO);
        }
        WechatOrderItemDTO result = wechatOrderItemService.save(wechatOrderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-order-items : get all the wechatOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatOrderItems in body
     */
    @GetMapping("/wechat-order-items")
    @Timed
    public ResponseEntity<List<WechatOrderItemDTO>> getAllWechatOrderItems(Pageable pageable) {
        log.debug("REST request to get a page of WechatOrderItems");
        Page<WechatOrderItemDTO> page = wechatOrderItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-order-items/:id : get the "id" wechatOrderItem.
     *
     * @param id the id of the wechatOrderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatOrderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-order-items/{id}")
    @Timed
    public ResponseEntity<WechatOrderItemDTO> getWechatOrderItem(@PathVariable Long id) {
        log.debug("REST request to get WechatOrderItem : {}", id);
        WechatOrderItemDTO wechatOrderItemDTO = wechatOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatOrderItemDTO));
    }

    /**
     * DELETE  /wechat-order-items/:id : delete the "id" wechatOrderItem.
     *
     * @param id the id of the wechatOrderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete WechatOrderItem : {}", id);
        wechatOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
