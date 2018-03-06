package com.wechat.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wechat.backend.service.WechatProductImageService;
import com.wechat.backend.service.dto.WechatProductImageDTO;
import com.wechat.backend.web.rest.errors.BadRequestAlertException;
import com.wechat.backend.web.rest.util.HeaderUtil;
import com.wechat.backend.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WechatProductImage.
 */
@RestController
@RequestMapping("/api")
public class WechatProductImageResource {

    private final Logger log = LoggerFactory.getLogger(WechatProductImageResource.class);
    private static final String ROOT="upload";
    private static final String ENTITY_NAME = "wechatProductImage";

    private final WechatProductImageService wechatProductImageService;
    private final ResourceLoader resourceLoader;

    public WechatProductImageResource(WechatProductImageService wechatProductImageService,ResourceLoader resourceLoader) {
        this.wechatProductImageService = wechatProductImageService;
        this.resourceLoader=resourceLoader;
    }

    /**
     * POST  /wechat-product-images : Create a new wechatProductImage.
     *
     * @param wechatProductImageDTO the wechatProductImageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatProductImageDTO, or with status 400 (Bad Request) if the wechatProductImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-product-images")
    @Timed
    public ResponseEntity<WechatProductImageDTO> createWechatProductImage(@Valid @RequestBody WechatProductImageDTO wechatProductImageDTO) throws URISyntaxException {
        log.debug("REST request to save WechatProductImage : {}", wechatProductImageDTO);
        if (wechatProductImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatProductImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WechatProductImageDTO result = wechatProductImageService.save(wechatProductImageDTO);
        return ResponseEntity.created(new URI("/api/wechat-product-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/wechat-product-images/upload")
    @Timed
    public ResponseEntity<WechatProductImageDTO> createWechatProductImage(@RequestParam("file") MultipartFile file, @RequestParam("productId") Long productId) throws URISyntaxException, IOException {
        WechatProductImageDTO wechatProductImageDTO=new WechatProductImageDTO();
        String fileName = file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        if(!Paths.get(ROOT).toFile().exists()){
            Files.createDirectory(Paths.get(ROOT));
        }
        fileName=System.nanoTime()+ext;
        Files.copy(file.getInputStream(), Paths.get("upload", fileName));
        //BASE64Encoder encoder = new BASE64Encoder();
        //wechatProductImageDTO.setImageContent("data:image/png;base64,"+encoder.encode(file.getBytes()));
        wechatProductImageDTO.setImageUrl(fileName);
        wechatProductImageDTO.setWechatProductId(productId);
        WechatProductImageDTO result = wechatProductImageService.save(wechatProductImageDTO);
        return ResponseEntity.created(new URI("/api/wechat-product-images/upload" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * PUT  /wechat-product-images : Updates an existing wechatProductImage.
     *
     * @param wechatProductImageDTO the wechatProductImageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatProductImageDTO,
     * or with status 400 (Bad Request) if the wechatProductImageDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatProductImageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-product-images")
    @Timed
    public ResponseEntity<WechatProductImageDTO> updateWechatProductImage(@Valid @RequestBody WechatProductImageDTO wechatProductImageDTO) throws URISyntaxException {
        log.debug("REST request to update WechatProductImage : {}", wechatProductImageDTO);
        if (wechatProductImageDTO.getId() == null) {
            return createWechatProductImage(wechatProductImageDTO);
        }
        WechatProductImageDTO result = wechatProductImageService.save(wechatProductImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatProductImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-product-images : get all the wechatProductImages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatProductImages in body
     */
    @GetMapping("/wechat-product-images")
    @Timed
    public ResponseEntity<List<WechatProductImageDTO>> getAllWechatProductImages(Pageable pageable) {
        log.debug("REST request to get a page of WechatProductImages");
        Page<WechatProductImageDTO> page = wechatProductImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-product-images");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-product-images/:id : get the "id" wechatProductImage.
     *
     * @param id the id of the wechatProductImageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatProductImageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-product-images/{id}")
    @Timed
    public ResponseEntity<WechatProductImageDTO> getWechatProductImage(@PathVariable Long id) {
        log.debug("REST request to get WechatProductImage : {}", id);
        WechatProductImageDTO wechatProductImageDTO = wechatProductImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatProductImageDTO));
    }

    /**
     * DELETE  /wechat-product-images/:id : delete the "id" wechatProductImage.
     *
     * @param id the id of the wechatProductImageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-product-images/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatProductImage(@PathVariable Long id) {
        log.debug("REST request to delete WechatProductImage : {}", id);
        wechatProductImageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
