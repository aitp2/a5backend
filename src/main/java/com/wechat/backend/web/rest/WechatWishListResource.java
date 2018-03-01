package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.service.WechatWishListService;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import com.wechat.backend.service.dto.WechatWishListDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing WechatWishList.
 */
@RestController
@RequestMapping("/api")
public class WechatWishListResource {

    private final Logger log = LoggerFactory.getLogger(WechatWishListResource.class);

    private static final String ENTITY_NAME = "wechatWishList";

    private final WechatWishListService wechatWishListService;

    public WechatWishListResource(WechatWishListService wechatWishListService) {
        this.wechatWishListService = wechatWishListService;
    }

    /**
     * POST  /wechat-wish-lists : Create a new wechatWishList.
     *
     * @param wechatWishListDTO the wechatWishListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatWishListDTO, or with status 400 (Bad Request) if the wechatWishList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-wish-lists")
    @Timed
    public ResponseEntity<WechatWishListDTO> createWechatWishList(@RequestBody WechatWishListDTO wechatWishListDTO) throws URISyntaxException {
        log.debug("REST request to save WechatWishList : {}", wechatWishListDTO);
        if (wechatWishListDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatWishList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WechatWishListDTO result = wechatWishListService.save(wechatWishListDTO);
        return ResponseEntity.created(new URI("/api/wechat-wish-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-wish-lists : Updates an existing wechatWishList.
     *
     * @param wechatWishListDTO the wechatWishListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatWishListDTO,
     * or with status 400 (Bad Request) if the wechatWishListDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatWishListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-wish-lists")
    @Timed
    public ResponseEntity<WechatWishListDTO> updateWechatWishList(@RequestBody WechatWishListDTO wechatWishListDTO) throws URISyntaxException {
        log.debug("REST request to update WechatWishList : {}", wechatWishListDTO);
        if (wechatWishListDTO.getId() == null) {
            return createWechatWishList(wechatWishListDTO);
        }
        WechatWishListDTO result = wechatWishListService.save(wechatWishListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatWishListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-wish-lists : get all the wechatWishLists.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of wechatWishLists in body
     */
    @GetMapping("/wechat-wish-lists")
    @Timed
    public ResponseEntity<List<WechatWishListDTO>> getAllWechatWishLists(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("wechatuser-is-null".equals(filter)) {
            log.debug("REST request to get all WechatWishLists where wechatUser is null");
            return new ResponseEntity<>(wechatWishListService.findAllWhereWechatUserIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of WechatWishLists");
        Page<WechatWishListDTO> page = wechatWishListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-wish-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-wish-lists/:id : get the "id" wechatWishList.
     *
     * @param id the id of the wechatWishListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatWishListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-wish-lists/{id}")
    @Timed
    public ResponseEntity<WechatWishListDTO> getWechatWishList(@PathVariable Long id) {
        log.debug("REST request to get WechatWishList : {}", id);
        WechatWishListDTO wechatWishListDTO = wechatWishListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatWishListDTO));
    }

    /**
     * DELETE  /wechat-wish-lists/:id : delete the "id" wechatWishList.
     *
     * @param id the id of the wechatWishListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-wish-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatWishList(@PathVariable Long id) {
        log.debug("REST request to delete WechatWishList : {}", id);
        wechatWishListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
