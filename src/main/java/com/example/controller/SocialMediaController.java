package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    /**
     * Endpoint to register a new user account.
     * Validates the username and password, checks for duplicate usernames, and saves the account if valid.
     * 
     * @param account The account object containing username and password.
     * @return ResponseEntity with the saved account or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        // Validate username and password
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username cannot be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 4 characters long.");
        }

        // Check for duplicate username
        if (accountService.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        // Save account and return response
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    /**
     * Endpoint to log in a user.
     * Validates the username and password, and checks if the credentials match an existing account.
     * 
     * @param account The account object containing username and password.
     * @return ResponseEntity with the account object if login is successful, or an error message if not.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) {
        // Validate username and password
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username cannot be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 4 characters long.");
        }

        // Check for existing account
        Account existingAccount = accountService.findByUsername(account.getUsername()).orElse(null);
        if (existingAccount == null || !existingAccount.getPassword().equals(account.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        // Return the Account object as the response body
        return ResponseEntity.ok(existingAccount);
    }

    /**
     * Endpoint to create a new message.
     * Validates the message text and the user who posted it, then saves the message if valid.
     * 
     * @param message The message object containing the text and the user ID of the poster.
     * @return ResponseEntity with the saved message or an error message.
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        // Validate messageText
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot be blank.");
        }
        if (message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot exceed 255 characters.");
        }

        // Validate postedBy (user must exist)
        Account existingAccount = accountService.findById(message.getPostedBy()).orElse(null);
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PostedBy (user ID) cannot be null. User does not exist.");
        }

        // Save the message and return the response
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * Endpoint to retrieve all messages.
     * Fetches all messages from the database.
     * 
     * @return ResponseEntity with a list of all messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        // Fetch all messages
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Endpoint to retrieve a specific message by its ID.
     * Fetches the message if it exists, or returns null if it does not.
     * 
     * @param messageId The ID of the message to retrieve.
     * @return ResponseEntity with the message object or null.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        // Fetch message by ID
        Message message = messageService.getMessageById(messageId);

        if (message == null) {
            return ResponseEntity.status(200).body(null);
        }

        return ResponseEntity.ok(message);
    }

    /**
     * Endpoint to delete a specific message by its ID.
     * Deletes the message if it exists, or returns an empty response if it does not.
     * 
     * @param messageId The ID of the message to delete.
     * @return ResponseEntity with 1 if the message was deleted, or an empty response if not.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        // Delete the message
        Message deletedMessage = messageService.deleteMessage(messageId);

        if (deletedMessage == null) {
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    /**
     * Endpoint to update a specific message by its ID.
     * Validates the new message text, fetches the existing message, and updates it if valid.
     * 
     * @param messageId The ID of the message to update.
     * @param message The message object containing the new text.
     * @return ResponseEntity with 1 if the message was updated, or an error message if not.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        // Validate messageText
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot be blank.");
        }
        if (message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot exceed 255 characters.");
        }

        // Fetch the existing message
        Message existingMessage = messageService.getMessageById(messageId);
        if (existingMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not found.");
        }

        // Update the message
        existingMessage.setMessageText(message.getMessageText());
        messageService.saveMessage(existingMessage);

        return ResponseEntity.ok(1);
    }

    /**
     * Endpoint to retrieve all messages posted by a specific user.
     * Validates if the user exists, then fetches all messages associated with the user's account ID.
     * 
     * @param accountId The ID of the user whose messages are to be retrieved.
     * @return ResponseEntity with a list of messages or an empty list if no messages exist.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccountId(@PathVariable Integer accountId) {
        // Validate if the account exists
        Account existingAccount = accountService.findById(accountId).orElse(null);
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }

        // Fetch messages by account ID
        List<Message> messages = messageService.getMessagesByAccountId(accountId);

        // Return the list of messages (empty list if no messages exist)
        return ResponseEntity.ok(messages);
    }

}
