package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param postedBy The ID of the user who posted the messages.
     * @return A list of messages posted by the specified user.
     */
    List<Message> findAllByPostedBy(Integer postedBy);
}
