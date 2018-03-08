package com.wechat.backend.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Message entity.
 */
public class MessageDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @Size(max = 512)
    private String content;

    private Long relateTo;

    @Size(max = 128)
    private String userName;

    private Long userId;

    @Size(max = 1024)
    private String icon;

    private Long questionId;

    private Set<MessageDTO> feedbacks;

    public Set<MessageDTO> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<MessageDTO> feedbacks) {
        this.feedbacks = feedbacks;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRelateTo() {
        return relateTo;
    }

    public void setRelateTo(Long relateTo) {
        this.relateTo = relateTo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long messageId) {
        this.questionId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if(messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", relateTo=" + getRelateTo() +
            ", userName='" + getUserName() + "'" +
            ", userId=" + getUserId() +
            ", icon='" + getIcon() + "'" +
            "}";
    }
}
