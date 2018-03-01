package com.wechat.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WechatUser.
 */
@Entity
@Table(name = "wechat_user")
public class WechatUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微信openid
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "微信openid", required = true)
    @Column(name = "open_id", length = 255, nullable = false)
    private String openId;

    /**
     * 微信号
     */
    @Size(max = 128)
    @ApiModelProperty(value = "微信号")
    @Column(name = "wechat_code", length = 128)
    private String wechatCode;

    /**
     * 用户名
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户名")
    @Column(name = "user_name", length = 128)
    private String userName;

    /**
     * 手机号码
     */
    @Size(max = 128)
    @ApiModelProperty(value = "手机号码")
    @Column(name = "mobile_num", length = 128)
    private String mobileNum;

    @Size(max = 128)
    @Column(name = "project", length = 128)
    private String project;

    @Size(max = 128)
    @Column(name = "seat", length = 128)
    private String seat;

    @OneToOne
    @JoinColumn(unique = true)
    private WechatWishList wechatWishList;

    @OneToMany(mappedBy = "wechatUser")
    @JsonIgnore
    private Set<WechatProduct> wechatProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public WechatUser openId(String openId) {
        this.openId = openId;
        return this;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public WechatUser wechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
        return this;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getUserName() {
        return userName;
    }

    public WechatUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public WechatUser mobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
        return this;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getProject() {
        return project;
    }

    public WechatUser project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSeat() {
        return seat;
    }

    public WechatUser seat(String seat) {
        this.seat = seat;
        return this;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public WechatWishList getWechatWishList() {
        return wechatWishList;
    }

    public WechatUser wechatWishList(WechatWishList wechatWishList) {
        this.wechatWishList = wechatWishList;
        return this;
    }

    public void setWechatWishList(WechatWishList wechatWishList) {
        this.wechatWishList = wechatWishList;
    }

    public Set<WechatProduct> getWechatProducts() {
        return wechatProducts;
    }

    public WechatUser wechatProducts(Set<WechatProduct> wechatProducts) {
        this.wechatProducts = wechatProducts;
        return this;
    }

    public WechatUser addWechatProducts(WechatProduct wechatProduct) {
        this.wechatProducts.add(wechatProduct);
        wechatProduct.setWechatUser(this);
        return this;
    }

    public WechatUser removeWechatProducts(WechatProduct wechatProduct) {
        this.wechatProducts.remove(wechatProduct);
        wechatProduct.setWechatUser(null);
        return this;
    }

    public void setWechatProducts(Set<WechatProduct> wechatProducts) {
        this.wechatProducts = wechatProducts;
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
        WechatUser wechatUser = (WechatUser) o;
        if (wechatUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatUser{" +
            "id=" + getId() +
            ", openId='" + getOpenId() + "'" +
            ", wechatCode='" + getWechatCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", mobileNum='" + getMobileNum() + "'" +
            ", project='" + getProject() + "'" +
            ", seat='" + getSeat() + "'" +
            "}";
    }
}
