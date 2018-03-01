package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatProduct;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatProductRepository extends JpaRepository<WechatProduct, Long> {

}
