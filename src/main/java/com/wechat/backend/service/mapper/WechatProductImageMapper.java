package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatProductImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatProductImage and its DTO WechatProductImageDTO.
 */
@Mapper(componentModel = "spring", uses = {WechatProductMapper.class})
public interface WechatProductImageMapper extends EntityMapper<WechatProductImageDTO, WechatProductImage> {

    @Mapping(source = "wechatProduct.id", target = "wechatProductId")
    WechatProductImageDTO toDto(WechatProductImage wechatProductImage);

    @Mapping(source = "wechatProductId", target = "wechatProduct")
    WechatProductImage toEntity(WechatProductImageDTO wechatProductImageDTO);

    default WechatProductImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatProductImage wechatProductImage = new WechatProductImage();
        wechatProductImage.setId(id);
        return wechatProductImage;
    }
}
