package com.wechat.backend.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the WechatProductImage entity.
 */
public class WechatProductImageDTO implements Serializable {

    private Long id;

    @Size(max = 2048)
    private String imageUrl;

    @Size(max = 512)
    private String imageDesc;

    @Lob
    private String imageContent;

    private Long wechatProductId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public Long getWechatProductId() {
        return wechatProductId;
    }

    public void setWechatProductId(Long wechatProductId) {
        this.wechatProductId = wechatProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WechatProductImageDTO wechatProductImageDTO = (WechatProductImageDTO) o;
        if(wechatProductImageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatProductImageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatProductImageDTO{" +
            "id=" + getId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", imageDesc='" + getImageDesc() + "'" +
            ", imageContent='" + getImageContent() + "'" +
            "}";
    }
}
