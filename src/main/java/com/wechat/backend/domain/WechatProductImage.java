package com.wechat.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WechatProductImage.
 */
@Entity
@Table(name = "wechat_product_image")
public class WechatProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片地址
     */
    @Size(max = 2048)
    @ApiModelProperty(value = "图片地址")
    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    /**
     * 图片描述
     */
    @Size(max = 512)
    @ApiModelProperty(value = "图片描述")
    @Column(name = "image_desc", length = 512)
    private String imageDesc;

    /**
     * 图片内容 base64 当图片服务器有了可删除
     */
    @ApiModelProperty(value = "图片内容 base64 当图片服务器有了可删除")
    @Lob
    @Column(name = "image_content")
    private String imageContent;

    @ManyToOne
    @JsonIgnore
    private WechatProduct wechatProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WechatProductImage imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public WechatProductImage imageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
        return this;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImageContent() {
        return imageContent;
    }

    public WechatProductImage imageContent(String imageContent) {
        this.imageContent = imageContent;
        return this;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public WechatProduct getWechatProduct() {
        return wechatProduct;
    }

    public WechatProductImage wechatProduct(WechatProduct wechatProduct) {
        this.wechatProduct = wechatProduct;
        return this;
    }

    public void setWechatProduct(WechatProduct wechatProduct) {
        this.wechatProduct = wechatProduct;
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
        WechatProductImage wechatProductImage = (WechatProductImage) o;
        if (wechatProductImage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatProductImage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatProductImage{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", imageDesc='" + getImageDesc() + "'" +
            ", imageContent='" + getImageContent() + "'" +
            "}";
    }
}
