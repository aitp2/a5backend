package com.wechat.backend.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A WechatOrderItem.
 */
@Entity
@Table(name = "wechat_order_item")
public class WechatOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    /**
     * 质量
     */
    @ApiModelProperty(value = "质量")
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 零售价格
     */
    @ApiModelProperty(value = "零售价格")
    @Column(name = "retail_price", precision=10, scale=2)
    private BigDecimal retailPrice;

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品名称，业务字段
     */
    @Size(max = 255)
    @ApiModelProperty(value = "产品名称，业务字段")
    @Column(name = "product_name", length = 255)
    private String productName;

    @ManyToOne
    private WechatOrder wechatOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WechatOrderItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public WechatOrderItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public WechatOrderItem retailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public WechatOrderItem productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public WechatOrderItem productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public WechatOrder getWechatOrder() {
        return wechatOrder;
    }

    public WechatOrderItem wechatOrder(WechatOrder wechatOrder) {
        this.wechatOrder = wechatOrder;
        return this;
    }

    public void setWechatOrder(WechatOrder wechatOrder) {
        this.wechatOrder = wechatOrder;
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
        WechatOrderItem wechatOrderItem = (WechatOrderItem) o;
        if (wechatOrderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatOrderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatOrderItem{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", retailPrice=" + getRetailPrice() +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
