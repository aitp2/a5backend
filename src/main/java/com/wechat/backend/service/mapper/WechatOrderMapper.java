package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatOrder and its DTO WechatOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WechatOrderMapper extends EntityMapper<WechatOrderDTO, WechatOrder> {


    @Mapping(target = "wechatOrderItems", ignore = true)
    WechatOrder toEntity(WechatOrderDTO wechatOrderDTO);

    default WechatOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatOrder wechatOrder = new WechatOrder();
        wechatOrder.setId(id);
        return wechatOrder;
    }
}
