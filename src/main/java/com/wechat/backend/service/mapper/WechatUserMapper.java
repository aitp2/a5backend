package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.WechatUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatUser and its DTO WechatUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WechatUserMapper extends EntityMapper<WechatUserDTO, WechatUser> {


    @Mapping(target = "wechatProducts", ignore = true)
    WechatUser toEntity(WechatUserDTO wechatUserDTO);

    default WechatUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatUser wechatUser = new WechatUser();
        wechatUser.setId(id);
        return wechatUser;
    }
}
