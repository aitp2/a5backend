package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatOrderRepository extends JpaRepository<WechatOrder, Long> {
    Page<WechatOrder> findAllByCustomerId(Pageable pageable, Long customerId);

}
