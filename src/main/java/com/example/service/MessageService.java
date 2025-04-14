package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Validates the content of a message.
     * 
     * @param message The message object to be validated.
     * @return true if the message is valid, false otherwise.
     */
    public boolean isMessageValid(Message message) {
        // Validate message content
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            return false;
        }
        if (message.getMessageText().length() > 255) {
            return false;
        }
        return true;
    }

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
     * Updates a specific message by its ID.
     * 
     * @param messageId The ID of the message to update.
     * @return The updated message object if found, or null if not found.
     */
    public Message updateMessageById(Integer messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            // Update the message content
            message.setMessageText("Updated message text");
            return messageRepository.save(message);
        }
        return null;
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
        // Validate if the account exists
        Account existingAccount = accountService.getAccountById(accountId).orElse(null);
        if (existingAccount == null) {
            return new ArrayList<>();
        }

        return messageRepository.findAllByPostedBy(accountId);
    }
}
