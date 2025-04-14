package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Saves a new message to the database.
     * 
     * @param message The message object to be saved.
     * @return The saved message object.
     */
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a specific message by its ID.
     * 
     * @param messageId The ID of the message to retrieve.
     * @return The message object if found, or null if not found.
     */
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Deletes a specific message by its ID.
     * 
     * @param messageId The ID of the message to delete.
     * @return The deleted message object if it existed, or null if it did not exist.
     */
    public Message deleteMessage(Integer messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            messageRepository.delete(message);
        }
        return message;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param accountId The ID of the user whose messages are to be retrieved.
     * @return A list of messages posted by the user.
     */
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
