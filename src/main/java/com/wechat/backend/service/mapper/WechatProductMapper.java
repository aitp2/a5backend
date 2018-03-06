package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatProduct and its DTO WechatProductDTO.
 */
@Mapper(componentModel = "spring", uses = {WechatUserMapper.class})
public interface WechatProductMapper extends EntityMapper<WechatProductDTO, WechatProduct> {

    @Mapping(source = "wechatUser.id", target = "wechatUserId")
    @Mapping(source = "images", target = "images")
    WechatProductDTO toDto(WechatProduct wechatProduct);

    @Mapping(source = "wechatUserId", target = "wechatUser")
    WechatProduct toEntity(WechatProductDTO wechatProductDTO);

    default WechatProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatProduct wechatProduct = new WechatProduct();
        wechatProduct.setId(id);
        return wechatProduct;
    }
}
