package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatProductImage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatProductImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatProductImageRepository extends JpaRepository<WechatProductImage, Long> {

}
