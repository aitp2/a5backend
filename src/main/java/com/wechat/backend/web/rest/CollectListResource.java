package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.domain.WechatProduct;
import com.wechat.backend.service.CollectListService;
import com.wechat.backend.service.WechatProductService;
import com.wechat.backend.service.dto.WechatProductDTO;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import com.wechat.backend.service.dto.CollectListDTO;
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

import java.util.*;

/**
 * REST controller for managing CollectList.
 */
@RestController
@RequestMapping("/api")
public class CollectListResource {

    private final Logger log = LoggerFactory.getLogger(CollectListResource.class);

    private static final String ENTITY_NAME = "collectList";

    private final CollectListService collectListService;

    private final WechatProductService wechatProductService;

    public CollectListResource(CollectListService collectListService,WechatProductService wechatProductService) {
        this.collectListService = collectListService;
        this.wechatProductService=wechatProductService;
    }

    /**
     * POST  /collect-lists : Create a new collectList.
     *
     * @param collectListDTO the collectListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collectListDTO, or with status 400 (Bad Request) if the collectList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collect-lists")
    @Timed
    public ResponseEntity<CollectListDTO> createCollectList(@RequestBody CollectListDTO collectListDTO) throws URISyntaxException {
        log.debug("REST request to save CollectList : {}", collectListDTO);
        if (collectListDTO.getId() != null) {
            throw new BadRequestAlertException("A new collectList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectListDTO result = collectListService.save(collectListDTO);
        return ResponseEntity.created(new URI("/api/collect-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collect-lists : Updates an existing collectList.
     *
     * @param collectListDTO the collectListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collectListDTO,
     * or with status 400 (Bad Request) if the collectListDTO is not valid,
     * or with status 500 (Internal Server Error) if the collectListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collect-lists")
    @Timed
    public ResponseEntity<CollectListDTO> updateCollectList(@RequestBody CollectListDTO collectListDTO) throws URISyntaxException {
        log.debug("REST request to update CollectList : {}", collectListDTO);
        if (collectListDTO.getId() == null) {
            return createCollectList(collectListDTO);
        }
        CollectListDTO result = collectListService.save(collectListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collectListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collect-lists : get all the collectLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of collectLists in body
     */
    @GetMapping("/collect-lists")
    @Timed
    public ResponseEntity<List<CollectListDTO>> getAllCollectLists(Pageable pageable) {
        log.debug("REST request to get a page of CollectLists");
        Page<CollectListDTO> page = collectListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collect-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collect-lists/:id : get the "id" collectList.
     *
     * @param id the id of the collectListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collectListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/collect-lists/{id}")
    @Timed
    public ResponseEntity<CollectListDTO> getCollectList(@PathVariable Long id) {
        log.debug("REST request to get CollectList : {}", id);
        CollectListDTO collectListDTO = collectListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collectListDTO));
    }
    @GetMapping("/collect-lists/mine/{userId}")
    @Timed
    public ResponseEntity<CollectListDTO> getCollectListByUser(@PathVariable Long userId) {
        log.debug("REST request to get CollectList : {}", userId);
        CollectListDTO collectListDTO = collectListService.findOneByUserId(userId);
        if(collectListDTO==null){
            collectListDTO=new CollectListDTO();
            collectListDTO.setUserId(userId);
            collectListDTO=collectListService.save(collectListDTO);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collectListDTO));
    }
    /**
     * DELETE  /collect-lists/:id : delete the "id" collectList.
     *
     * @param id the id of the collectListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collect-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollectList(@PathVariable Long id) {
        log.debug("REST request to delete CollectList : {}", id);
        collectListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @PutMapping("/collect-lists/{type}/{userId}/{productId}")
    @Timed
    public ResponseEntity<CollectListDTO> collects(@PathVariable Long userId,@PathVariable Long productId,@PathVariable Integer type) {
        CollectListDTO collectListDTO = collectListService.findOneByUserId(userId);
        if(collectListDTO==null){
            collectListDTO=new CollectListDTO();
            collectListDTO.setUserId(userId);
            collectListDTO=collectListService.save(collectListDTO);
        }
        Set<WechatProductDTO> collectProducts=collectListDTO.getCollectProducts()==null?new HashSet<>():collectListDTO.getCollectProducts();
        if(0==type){
            WechatProductDTO productDTO=wechatProductService.findOne(productId);
            collectProducts.add(productDTO);
            int times=productDTO.getCollectTimes()==null?0:productDTO.getCollectTimes();
            times= times+1;
            productDTO.setCollectTimes(times);
            wechatProductService.save(productDTO);
        }else{
            Iterator<WechatProductDTO> iterator=collectProducts.iterator();
            while(iterator.hasNext()){
                WechatProductDTO productDTO=iterator.next();
                if(productId.equals(productDTO.getId())){
                    WechatProductDTO wechatProductDTO=wechatProductService.findOne(productId);
                    int times=productDTO.getCollectTimes()==null?0:productDTO.getCollectTimes();
                    times= times-1;
                    wechatProductDTO.setCollectTimes(times);
                    wechatProductService.save(wechatProductDTO);
                    iterator.remove();
                }
            }
        }
        CollectListDTO result = collectListService.save(collectListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collectListDTO.getId().toString()))
            .body(result);
    }

}
