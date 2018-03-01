package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatWishList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatWishList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatWishListRepository extends JpaRepository<WechatWishList, Long> {

}
