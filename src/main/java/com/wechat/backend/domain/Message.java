package com.wechat.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 留言内容
     */
    @Size(max = 512)
    @ApiModelProperty(value = "留言内容")
    @Column(name = "content", length = 512)
    private String content;

    /**
     * 关联到内容的ID，初步为产品的ID
     */
    @ApiModelProperty(value = "关联到内容的ID，初步为产品的ID")
    @Column(name = "relate_to")
    private Long relateTo;

    /**
     * 用户昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户昵称")
    @Column(name = "user_name", length = 128)
    private String userName;

    /**
     * 评论人的ID
     */
    @ApiModelProperty(value = "评论人的ID")
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private Set<Message> feedbacks = new HashSet<>();

    @ManyToOne
    private Message question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Message content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRelateTo() {
        return relateTo;
    }

    public Message relateTo(Long relateTo) {
        this.relateTo = relateTo;
        return this;
    }

    public void setRelateTo(Long relateTo) {
        this.relateTo = relateTo;
    }

    public String getUserName() {
        return userName;
    }

    public Message userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public Message userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Message> getFeedbacks() {
        return feedbacks;
    }

    public Message feedbacks(Set<Message> messages) {
        this.feedbacks = messages;
        return this;
    }

    public Message addFeedbacks(Message message) {
        this.feedbacks.add(message);
        message.setQuestion(this);
        return this;
    }

    public Message removeFeedbacks(Message message) {
        this.feedbacks.remove(message);
        message.setQuestion(null);
        return this;
    }

    public void setFeedbacks(Set<Message> messages) {
        this.feedbacks = messages;
    }

    public Message getQuestion() {
        return question;
    }

    public Message question(Message message) {
        this.question = message;
        return this;
    }

    public void setQuestion(Message message) {
        this.question = message;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", relateTo=" + getRelateTo() +
            ", userName='" + getUserName() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
