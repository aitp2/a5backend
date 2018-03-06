package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatOrderItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatOrderItemRepository extends JpaRepository<WechatOrderItem, Long> {

}
