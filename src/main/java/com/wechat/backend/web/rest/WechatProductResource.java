package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import com.wechat.backend.service.dto.WechatProductDTO;
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
 * REST controller for managing WechatProduct.
 */
@RestController
@RequestMapping("/api")
public class WechatProductResource {

    private final Logger log = LoggerFactory.getLogger(WechatProductResource.class);

    private static final String ENTITY_NAME = "wechatProduct";

    private final WechatProductService wechatProductService;

    public WechatProductResource(WechatProductService wechatProductService) {
        this.wechatProductService = wechatProductService;
    }

    /**
     * POST  /wechat-products : Create a new wechatProduct.
     *
     * @param wechatProductDTO the wechatProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatProductDTO, or with status 400 (Bad Request) if the wechatProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-products")
    @Timed
    public ResponseEntity<WechatProductDTO> createWechatProduct(@Valid @RequestBody WechatProductDTO wechatProductDTO) throws URISyntaxException {
        log.debug("REST request to save WechatProduct : {}", wechatProductDTO);
        if (wechatProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WechatProductDTO result = wechatProductService.save(wechatProductDTO);
        return ResponseEntity.created(new URI("/api/wechat-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-products : Updates an existing wechatProduct.
     *
     * @param wechatProductDTO the wechatProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatProductDTO,
     * or with status 400 (Bad Request) if the wechatProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-products")
    @Timed
    public ResponseEntity<WechatProductDTO> updateWechatProduct(@Valid @RequestBody WechatProductDTO wechatProductDTO) throws URISyntaxException {
        log.debug("REST request to update WechatProduct : {}", wechatProductDTO);
        if (wechatProductDTO.getId() == null) {
            return createWechatProduct(wechatProductDTO);
        }
        WechatProductDTO result = wechatProductService.save(wechatProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-products : get all the wechatProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatProducts in body
     */
    @GetMapping("/wechat-products")
    @Timed
    public ResponseEntity<List<WechatProductDTO>> getAllWechatProducts(Pageable pageable) {
        log.debug("REST request to get a page of WechatProducts");
        Page<WechatProductDTO> page = wechatProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-products/:id : get the "id" wechatProduct.
     *
     * @param id the id of the wechatProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-products/{id}")
    @Timed
    public ResponseEntity<WechatProductDTO> getWechatProduct(@PathVariable Long id) {
        log.debug("REST request to get WechatProduct : {}", id);
        WechatProductDTO wechatProductDTO = wechatProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatProductDTO));
    }

    /**
     * DELETE  /wechat-products/:id : delete the "id" wechatProduct.
     *
     * @param id the id of the wechatProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatProduct(@PathVariable Long id) {
        log.debug("REST request to delete WechatProduct : {}", id);
        wechatProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
