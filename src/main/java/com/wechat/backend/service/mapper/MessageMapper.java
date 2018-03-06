package com.wechat.backend.service.mapper;

import com.wechat.backend.domain.*;
import com.wechat.backend.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "feedbacks", target = "feedbacks")
    MessageDTO toDto(Message message);

    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(source = "questionId", target = "question")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
