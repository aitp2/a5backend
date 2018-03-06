package com.wechat.backend.service;

import com.wechat.backend.domain.WechatOrderItem;
import com.wechat.backend.repository.WechatOrderItemRepository;
import com.wechat.backend.service.dto.WechatOrderItemDTO;
import com.wechat.backend.service.mapper.WechatOrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WechatOrderItem.
 */
@Service
@Transactional
public class WechatOrderItemService {

    private final Logger log = LoggerFactory.getLogger(WechatOrderItemService.class);

    private final WechatOrderItemRepository wechatOrderItemRepository;

    private final WechatOrderItemMapper wechatOrderItemMapper;

    public WechatOrderItemService(WechatOrderItemRepository wechatOrderItemRepository, WechatOrderItemMapper wechatOrderItemMapper) {
        this.wechatOrderItemRepository = wechatOrderItemRepository;
        this.wechatOrderItemMapper = wechatOrderItemMapper;
    }

    /**
     * Save a wechatOrderItem.
     *
     * @param wechatOrderItemDTO the entity to save
     * @return the persisted entity
     */
    public WechatOrderItemDTO save(WechatOrderItemDTO wechatOrderItemDTO) {
        log.debug("Request to save WechatOrderItem : {}", wechatOrderItemDTO);
        WechatOrderItem wechatOrderItem = wechatOrderItemMapper.toEntity(wechatOrderItemDTO);
        wechatOrderItem = wechatOrderItemRepository.save(wechatOrderItem);
        return wechatOrderItemMapper.toDto(wechatOrderItem);
    }

    /**
     * Get all the wechatOrderItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatOrderItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatOrderItems");
        return wechatOrderItemRepository.findAll(pageable)
            .map(wechatOrderItemMapper::toDto);
    }

    /**
     * Get one wechatOrderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatOrderItemDTO findOne(Long id) {
        log.debug("Request to get WechatOrderItem : {}", id);
        WechatOrderItem wechatOrderItem = wechatOrderItemRepository.findOne(id);
        return wechatOrderItemMapper.toDto(wechatOrderItem);
    }

    /**
     * Delete the wechatOrderItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatOrderItem : {}", id);
        wechatOrderItemRepository.delete(id);
    }
}
