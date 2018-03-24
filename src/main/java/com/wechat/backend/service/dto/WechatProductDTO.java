package com.wechat.backend.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WechatProduct entity.
 */
public class WechatProductDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String productCode;

    @NotNull
    @Size(max = 255)
    private String productName;

    @Size(max = 1024)
    private String metaDesc;

    @Size(max = 1024)
    private String image;

    private BigDecimal originalPrice;

    private BigDecimal price;

    private BigDecimal productQuantity;

    private Boolean platformProduct;

    private Boolean sellOut;

    private Boolean goLive;

    private Integer collectTimes;

    private Long wechatUserId;
    public Set<WechatProductImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<WechatProductImageDTO> images) {
        this.images = images;
    }

    private Set<WechatProductImageDTO> images; 
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(BigDecimal productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Boolean isPlatformProduct() {
        return platformProduct;
    }

    public void setPlatformProduct(Boolean platformProduct) {
        this.platformProduct = platformProduct;
    }

    public Boolean isSellOut() {
        return sellOut;
    }

    public void setSellOut(Boolean sellOut) {
        this.sellOut = sellOut;
    }

    public Boolean isGoLive() {
        return goLive;
    }

    public void setGoLive(Boolean goLive) {
        this.goLive = goLive;
    }

    public Integer getCollectTimes() {
        return collectTimes;
    }

    public void setCollectTimes(Integer collectTimes) {
        this.collectTimes = collectTimes;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WechatProductDTO wechatProductDTO = (WechatProductDTO) o;
        if(wechatProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatProductDTO{" +
            "id=" + getId() +
            ", productCode='" + getProductCode() + "'" +
            ", productName='" + getProductName() + "'" +
            ", metaDesc='" + getMetaDesc() + "'" +
            ", image='" + getImage() + "'" +
            ", originalPrice=" + getOriginalPrice() +
            ", price=" + getPrice() +
            ", productQuantity=" + getProductQuantity() +
            ", platformProduct='" + isPlatformProduct() + "'" +
            ", sellOut='" + isSellOut() + "'" +
            ", goLive='" + isGoLive() + "'" +
            ", collectTimes=" + getCollectTimes() +
            "}";
    }
}
