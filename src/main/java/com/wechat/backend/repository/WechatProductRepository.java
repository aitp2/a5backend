package com.wechat.backend.repository;

import com.wechat.backend.domain.WechatProduct;
import com.wechat.backend.domain.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatProduct entity.
 */
@Repository
public interface WechatProductRepository extends JpaRepository<WechatProduct, Long> {

    Page<WechatProduct> findAllByWechatUser(Pageable pageable, WechatUser wechatUser);

    Page<WechatProduct> findAllBySellOutIsFalse(Pageable pageable);

}
