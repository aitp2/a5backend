package com.wechat.backend.repository;

import com.wechat.backend.domain.Message;
import com.wechat.backend.service.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRelateTo(Long relateTo);
}
