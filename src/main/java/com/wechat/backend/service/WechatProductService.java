package com.wechat.backend.service;

import com.wechat.backend.domain.WechatProduct;
import com.wechat.backend.repository.WechatProductRepository;
import com.wechat.backend.service.dto.WechatProductDTO;
import com.wechat.backend.service.mapper.WechatProductMapper;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WechatProduct.
 */
@Service
@Transactional
public class WechatProductService {

    private final Logger log = LoggerFactory.getLogger(WechatProductService.class);

    private final WechatProductRepository wechatProductRepository;

    private final WechatProductMapper wechatProductMapper;

    public WechatProductService(WechatProductRepository wechatProductRepository, WechatProductMapper wechatProductMapper) {
        this.wechatProductRepository = wechatProductRepository;
        this.wechatProductMapper = wechatProductMapper;
    }

    /**
     * Save a wechatProduct.
     *
     * @param wechatProductDTO the entity to save
     * @return the persisted entity
     */
    public WechatProductDTO save(WechatProductDTO wechatProductDTO) {
        log.debug("Request to save WechatProduct : {}", wechatProductDTO);
        WechatProduct wechatProduct = wechatProductMapper.toEntity(wechatProductDTO);
        wechatProduct = wechatProductRepository.save(wechatProduct);
        return wechatProductMapper.toDto(wechatProduct);
    }

    /**
     * Get all the wechatProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatProducts");
        Page<WechatProduct> producuts = wechatProductRepository.findAll(pageable);
        for(WechatProduct product:producuts){
            if(!Hibernate.isInitialized(product.getImages())){
               Hibernate.initialize(product.getImages());
            }
        }
        return producuts.map(wechatProductMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<WechatProductDTO> findAllByUserId(Pageable pageable,Long wechatUserId) {
        log.debug("Request to get all WechatProducts");
        Page<WechatProduct> producuts = wechatProductRepository.findAllByWechatUser(pageable,wechatUserId);
        for(WechatProduct product:producuts){
            if(!Hibernate.isInitialized(product.getImages())){
                Hibernate.initialize(product.getImages());
            }
        }
        return producuts.map(wechatProductMapper::toDto);
    }

    /**
     * Get one wechatProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatProductDTO findOne(Long id) {
        log.debug("Request to get WechatProduct : {}", id);
        WechatProduct wechatProduct = wechatProductRepository.findOne(id);
        return wechatProductMapper.toDto(wechatProduct);
    }

    /**
     * Delete the wechatProduct by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatProduct : {}", id);
        wechatProductRepository.delete(id);
    }
}
