package com.wechat.backend.service;

import com.wechat.backend.domain.CollectList;
import com.wechat.backend.repository.CollectListRepository;
import com.wechat.backend.service.dto.CollectListDTO;
import com.wechat.backend.service.mapper.CollectListMapper;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CollectList.
 */
@Service
@Transactional
public class CollectListService {

    private final Logger log = LoggerFactory.getLogger(CollectListService.class);

    private final CollectListRepository collectListRepository;

    private final CollectListMapper collectListMapper;

    public CollectListService(CollectListRepository collectListRepository, CollectListMapper collectListMapper) {
        this.collectListRepository = collectListRepository;
        this.collectListMapper = collectListMapper;
    }

    /**
     * Save a collectList.
     *
     * @param collectListDTO the entity to save
     * @return the persisted entity
     */
    public CollectListDTO save(CollectListDTO collectListDTO) {
        log.debug("Request to save CollectList : {}", collectListDTO);
        CollectList collectList = collectListMapper.toEntity(collectListDTO);
        collectList = collectListRepository.save(collectList);
        return collectListMapper.toDto(collectList);
    }

    /**
     * Get all the collectLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CollectListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CollectLists");
        return collectListRepository.findAll(pageable)
            .map(collectListMapper::toDto);
    }

    /**
     * Get one collectList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CollectListDTO findOne(Long id) {
        log.debug("Request to get CollectList : {}", id);
        CollectList collectList = collectListRepository.findOneWithEagerRelationships(id);
        if(collectList!=null&& !Hibernate.isInitialized(collectList.getCollectProducts())){
            Hibernate.initialize(collectList.getCollectProducts());
        }
        return collectListMapper.toDto(collectList);
    }
    @Transactional(readOnly = true)
    public CollectListDTO findOneByUserId(Long userId) {
        log.debug("Request to get CollectList : {}", userId);
        CollectList collectList = collectListRepository.findByUserId(userId);
        if(collectList!=null&& !Hibernate.isInitialized(collectList.getCollectProducts())){
            Hibernate.initialize(collectList.getCollectProducts());
        }
        return collectListMapper.toDto(collectList);
    }

    /**
     * Delete the collectList by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollectList : {}", id);
        collectListRepository.delete(id);
    }
}
