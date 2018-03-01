package com.wechat.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "wechat_wish_list")
public class WechatWishList extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "wechatWishList")
    @JsonIgnore
    private Set<WechatProduct> wishListProducts = new HashSet<>();

    @OneToOne(mappedBy = "wechatWishList")
    @JsonIgnore
    private WechatUser wechatUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<WechatProduct> getWishListProducts() {
        return wishListProducts;
    }

    public WechatWishList wishListProducts(Set<WechatProduct> wechatProducts) {
        this.wishListProducts = wechatProducts;
        return this;
    }

    public WechatWishList addWishListProducts(WechatProduct wechatProduct) {
        this.wishListProducts.add(wechatProduct);
        wechatProduct.setWechatWishList(this);
        return this;
    }

    public WechatWishList removeWishListProducts(WechatProduct wechatProduct) {
        this.wishListProducts.remove(wechatProduct);
        wechatProduct.setWechatWishList(null);
        return this;
    }

    public void setWishListProducts(Set<WechatProduct> wechatProducts) {
        this.wishListProducts = wechatProducts;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public WechatWishList wechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
        return this;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WechatWishList wechatWishList = (WechatWishList) o;
        if (wechatWishList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatWishList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatWishList{" +
            "id=" + getId() +
            "}";
    }
}
