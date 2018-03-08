package com.wechat.backend.service.dto;


import com.wechat.backend.domain.WechatOrderItem;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WechatOrder entity.
 */
public class WechatOrderDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @Size(max = 3)
    private String status;

    private BigDecimal orderAmount;

    private Long customerId;

    private Set<WechatOrderItemDTO> wechatOrderItems;

    public Set<WechatOrderItemDTO> getWechatOrderItems() {
        return wechatOrderItems;
    }

    public void setWechatOrderItems(Set<WechatOrderItemDTO> wechatOrderItems) {
        this.wechatOrderItems = wechatOrderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WechatOrderDTO wechatOrderDTO = (WechatOrderDTO) o;
        if(wechatOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatOrderDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", orderAmount=" + getOrderAmount() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
