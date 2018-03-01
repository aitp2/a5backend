package com.wechat.backend.service;

import com.wechat.backend.domain.WechatWishList;
import com.wechat.backend.repository.WechatWishListRepository;
import com.wechat.backend.service.dto.WechatWishListDTO;
import com.wechat.backend.service.mapper.WechatWishListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing WechatWishList.
 */
@Service
@Transactional
public class WechatWishListService {

    private final Logger log = LoggerFactory.getLogger(WechatWishListService.class);

    private final WechatWishListRepository wechatWishListRepository;

    private final WechatWishListMapper wechatWishListMapper;

    public WechatWishListService(WechatWishListRepository wechatWishListRepository, WechatWishListMapper wechatWishListMapper) {
        this.wechatWishListRepository = wechatWishListRepository;
        this.wechatWishListMapper = wechatWishListMapper;
    }

    /**
     * Save a wechatWishList.
     *
     * @param wechatWishListDTO the entity to save
     * @return the persisted entity
     */
    public WechatWishListDTO save(WechatWishListDTO wechatWishListDTO) {
        log.debug("Request to save WechatWishList : {}", wechatWishListDTO);
        WechatWishList wechatWishList = wechatWishListMapper.toEntity(wechatWishListDTO);
        wechatWishList = wechatWishListRepository.save(wechatWishList);
        return wechatWishListMapper.toDto(wechatWishList);
    }

    /**
     * Get all the wechatWishLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatWishListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatWishLists");
        return wechatWishListRepository.findAll(pageable)
            .map(wechatWishListMapper::toDto);
    }


    /**
     *  get all the wechatWishLists where WechatUser is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WechatWishListDTO> findAllWhereWechatUserIsNull() {
        log.debug("Request to get all wechatWishLists where WechatUser is null");
        return StreamSupport
            .stream(wechatWishListRepository.findAll().spliterator(), false)
            .filter(wechatWishList -> wechatWishList.getWechatUser() == null)
            .map(wechatWishListMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one wechatWishList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatWishListDTO findOne(Long id) {
        log.debug("Request to get WechatWishList : {}", id);
        WechatWishList wechatWishList = wechatWishListRepository.findOne(id);
        return wechatWishListMapper.toDto(wechatWishList);
    }

    /**
     * Delete the wechatWishList by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatWishList : {}", id);
        wechatWishListRepository.delete(id);
    }
}
