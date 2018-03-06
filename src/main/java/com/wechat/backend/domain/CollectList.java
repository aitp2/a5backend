package com.wechat.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CollectList.
 */
@Entity
@Table(name = "collect_list")
public class CollectList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToMany
    @JoinTable(name = "collect_list_collect_products",
               joinColumns = @JoinColumn(name="collect_lists_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="collect_products_id", referencedColumnName="id"))
    private Set<WechatProduct> collectProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public CollectList userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<WechatProduct> getCollectProducts() {
        return collectProducts;
    }

    public CollectList collectProducts(Set<WechatProduct> wechatProducts) {
        this.collectProducts = wechatProducts;
        return this;
    }

    public CollectList addCollectProducts(WechatProduct wechatProduct) {
        this.collectProducts.add(wechatProduct);
        return this;
    }

    public CollectList removeCollectProducts(WechatProduct wechatProduct) {
        this.collectProducts.remove(wechatProduct);
        return this;
    }

    public void setCollectProducts(Set<WechatProduct> wechatProducts) {
        this.collectProducts = wechatProducts;
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
        CollectList collectList = (CollectList) o;
        if (collectList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collectList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollectList{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
