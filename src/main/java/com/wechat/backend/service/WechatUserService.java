package com.wechat.backend.service;

import com.wechat.backend.domain.WechatUser;
import com.wechat.backend.repository.WechatUserRepository;
import com.wechat.backend.service.dto.WechatUserDTO;
import com.wechat.backend.service.mapper.WechatUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WechatUser.
 */
@Service
@Transactional
public class WechatUserService {

    private final Logger log = LoggerFactory.getLogger(WechatUserService.class);

    private final WechatUserRepository wechatUserRepository;

    private final WechatUserMapper wechatUserMapper;

    public WechatUserService(WechatUserRepository wechatUserRepository, WechatUserMapper wechatUserMapper) {
        this.wechatUserRepository = wechatUserRepository;
        this.wechatUserMapper = wechatUserMapper;
    }

    /**
     * Save a wechatUser.
     *
     * @param wechatUserDTO the entity to save
     * @return the persisted entity
     */
    public WechatUserDTO save(WechatUserDTO wechatUserDTO) {
        log.debug("Request to save WechatUser : {}", wechatUserDTO);
        WechatUser wechatUser = wechatUserMapper.toEntity(wechatUserDTO);
        wechatUser = wechatUserRepository.save(wechatUser);
        return wechatUserMapper.toDto(wechatUser);
    }

    /**
     * Get all the wechatUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatUsers");
        return wechatUserRepository.findAll(pageable)
            .map(wechatUserMapper::toDto);
    }

    /**
     * Get one wechatUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatUserDTO findOne(Long id) {
        log.debug("Request to get WechatUser : {}", id);
        WechatUser wechatUser = wechatUserRepository.findOne(id);
        return wechatUserMapper.toDto(wechatUser);
    }

    /**
     * Delete the wechatUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatUser : {}", id);
        wechatUserRepository.delete(id);
    }
   public WechatUserDTO findOneByOpenId(String openId){
        WechatUser wechatUser = wechatUserRepository.findOneByOpenId(openId);
        return wechatUser!=null?wechatUserMapper.toDto(wechatUser) : null;
    }}
