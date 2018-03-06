package com.wechat.backend.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WechatOrderItem entity.
 */
public class WechatOrderItemDTO implements Serializable {

    private Long id;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal retailPrice;

    private Long productId;

    private Long wechatOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWechatOrderId() {
        return wechatOrderId;
    }

    public void setWechatOrderId(Long wechatOrderId) {
        this.wechatOrderId = wechatOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WechatOrderItemDTO wechatOrderItemDTO = (WechatOrderItemDTO) o;
        if(wechatOrderItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatOrderItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatOrderItemDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", retailPrice=" + getRetailPrice() +
            ", productId=" + getProductId() +
            "}";
    }
}
