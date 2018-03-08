package com.wechat.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WechatOrder.
 */
@Entity
@Table(name = "wechat_order")
public class WechatOrder extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单状态
     */
    @Size(max = 3)
    @ApiModelProperty(value = "订单状态")
    @Column(name = "status", length = 3)
    private String status;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    @Column(name = "order_amount", precision=10, scale=2)
    private BigDecimal orderAmount;

    /**
     * 购买者的ID
     */
    @ApiModelProperty(value = "购买者的ID")
    @Column(name = "customer_id")
    private Long customerId;

    @OneToMany(mappedBy = "wechatOrder")
    private Set<WechatOrderItem> wechatOrderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public WechatOrder status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public WechatOrder orderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public WechatOrder customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<WechatOrderItem> getWechatOrderItems() {
        return wechatOrderItems;
    }

    public WechatOrder wechatOrderItems(Set<WechatOrderItem> wechatOrderItems) {
        this.wechatOrderItems = wechatOrderItems;
        return this;
    }

    public WechatOrder addWechatOrderItems(WechatOrderItem wechatOrderItem) {
        this.wechatOrderItems.add(wechatOrderItem);
        wechatOrderItem.setWechatOrder(this);
        return this;
    }

    public WechatOrder removeWechatOrderItems(WechatOrderItem wechatOrderItem) {
        this.wechatOrderItems.remove(wechatOrderItem);
        wechatOrderItem.setWechatOrder(null);
        return this;
    }

    public void setWechatOrderItems(Set<WechatOrderItem> wechatOrderItems) {
        this.wechatOrderItems = wechatOrderItems;
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
        WechatOrder wechatOrder = (WechatOrder) o;
        if (wechatOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatOrder{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
