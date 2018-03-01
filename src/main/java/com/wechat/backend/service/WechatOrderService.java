package com.wechat.backend.service;

import com.wechat.backend.domain.WechatOrder;
import com.wechat.backend.repository.WechatOrderRepository;
import com.wechat.backend.service.dto.WechatOrderDTO;
import com.wechat.backend.service.mapper.WechatOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WechatOrder.
 */
@Service
@Transactional
public class WechatOrderService {

    private final Logger log = LoggerFactory.getLogger(WechatOrderService.class);

    private final WechatOrderRepository wechatOrderRepository;

    private final WechatOrderMapper wechatOrderMapper;

    public WechatOrderService(WechatOrderRepository wechatOrderRepository, WechatOrderMapper wechatOrderMapper) {
        this.wechatOrderRepository = wechatOrderRepository;
        this.wechatOrderMapper = wechatOrderMapper;
    }

    /**
     * Save a wechatOrder.
     *
     * @param wechatOrderDTO the entity to save
     * @return the persisted entity
     */
    public WechatOrderDTO save(WechatOrderDTO wechatOrderDTO) {
        log.debug("Request to save WechatOrder : {}", wechatOrderDTO);
        WechatOrder wechatOrder = wechatOrderMapper.toEntity(wechatOrderDTO);
        wechatOrder = wechatOrderRepository.save(wechatOrder);
        return wechatOrderMapper.toDto(wechatOrder);
    }

    /**
     * Get all the wechatOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatOrders");
        return wechatOrderRepository.findAll(pageable)
            .map(wechatOrderMapper::toDto);
    }

    /**
     * Get one wechatOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatOrderDTO findOne(Long id) {
        log.debug("Request to get WechatOrder : {}", id);
        WechatOrder wechatOrder = wechatOrderRepository.findOne(id);
        return wechatOrderMapper.toDto(wechatOrder);
    }

    /**
     * Delete the wechatOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatOrder : {}", id);
        wechatOrderRepository.delete(id);
    }
}
