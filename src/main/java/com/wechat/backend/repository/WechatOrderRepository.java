package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatOrderRepository extends JpaRepository<WechatOrder, Long> {

}
