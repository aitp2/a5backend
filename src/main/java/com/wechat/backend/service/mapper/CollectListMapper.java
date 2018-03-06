package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.CollectListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollectList and its DTO CollectListDTO.
 */
@Mapper(componentModel = "spring", uses = {WechatProductMapper.class})
public interface CollectListMapper extends EntityMapper<CollectListDTO, CollectList> {



    default CollectList fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollectList collectList = new CollectList();
        collectList.setId(id);
        return collectList;
    }
}
