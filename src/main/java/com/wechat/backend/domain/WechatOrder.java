package com.wechat.backend.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @Column(name = "order_amount", precision=10, scale=2)
    private BigDecimal orderAmount;

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
            "}";
    }
}
