package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatOrderItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatOrderItem and its DTO WechatOrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {WechatOrderMapper.class})
public interface WechatOrderItemMapper extends EntityMapper<WechatOrderItemDTO, WechatOrderItem> {

    @Mapping(source = "wechatOrder.id", target = "wechatOrderId")
    WechatOrderItemDTO toDto(WechatOrderItem wechatOrderItem);

    @Mapping(source = "wechatOrderId", target = "wechatOrder")
    WechatOrderItem toEntity(WechatOrderItemDTO wechatOrderItemDTO);

    default WechatOrderItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatOrderItem wechatOrderItem = new WechatOrderItem();
        wechatOrderItem.setId(id);
        return wechatOrderItem;
    }
}
