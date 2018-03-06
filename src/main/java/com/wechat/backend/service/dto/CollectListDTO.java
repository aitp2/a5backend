package com.wechat.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollectList entity.
 */
public class CollectListDTO implements Serializable {

    private Long id;

    private Long userId;

    private Set<WechatProductDTO> collectProducts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<WechatProductDTO> getCollectProducts() {
        return collectProducts;
    }

    public void setCollectProducts(Set<WechatProductDTO> wechatProducts) {
        this.collectProducts = wechatProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollectListDTO collectListDTO = (CollectListDTO) o;
        if(collectListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collectListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollectListDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
