package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatWishListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatWishList and its DTO WechatWishListDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WechatWishListMapper extends EntityMapper<WechatWishListDTO, WechatWishList> {


    @Mapping(target = "wishListProducts", ignore = true)
    @Mapping(target = "wechatUser", ignore = true)
    WechatWishList toEntity(WechatWishListDTO wechatWishListDTO);

    default WechatWishList fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatWishList wechatWishList = new WechatWishList();
        wechatWishList.setId(id);
        return wechatWishList;
    }
}
