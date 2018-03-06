package com.wechat.backend.service;

import com.wechat.backend.domain.WechatProductImage;
import com.wechat.backend.repository.WechatProductImageRepository;
import com.wechat.backend.service.dto.WechatProductImageDTO;
import com.wechat.backend.service.mapper.WechatProductImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WechatProductImage.
 */
@Service
@Transactional
public class WechatProductImageService {

    private final Logger log = LoggerFactory.getLogger(WechatProductImageService.class);

    private final WechatProductImageRepository wechatProductImageRepository;

    private final WechatProductImageMapper wechatProductImageMapper;

    public WechatProductImageService(WechatProductImageRepository wechatProductImageRepository, WechatProductImageMapper wechatProductImageMapper) {
        this.wechatProductImageRepository = wechatProductImageRepository;
        this.wechatProductImageMapper = wechatProductImageMapper;
    }

    /**
     * Save a wechatProductImage.
     *
     * @param wechatProductImageDTO the entity to save
     * @return the persisted entity
     */
    public WechatProductImageDTO save(WechatProductImageDTO wechatProductImageDTO) {
        log.debug("Request to save WechatProductImage : {}", wechatProductImageDTO);
        WechatProductImage wechatProductImage = wechatProductImageMapper.toEntity(wechatProductImageDTO);
        wechatProductImage = wechatProductImageRepository.save(wechatProductImage);
        return wechatProductImageMapper.toDto(wechatProductImage);
    }

    /**
     * Get all the wechatProductImages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatProductImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatProductImages");
        return wechatProductImageRepository.findAll(pageable)
            .map(wechatProductImageMapper::toDto);
    }

    /**
     * Get one wechatProductImage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatProductImageDTO findOne(Long id) {
        log.debug("Request to get WechatProductImage : {}", id);
        WechatProductImage wechatProductImage = wechatProductImageRepository.findOne(id);
        return wechatProductImageMapper.toDto(wechatProductImage);
    }

    /**
     * Delete the wechatProductImage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatProductImage : {}", id);
        wechatProductImageRepository.delete(id);
    }
}
