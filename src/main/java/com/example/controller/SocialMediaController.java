package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

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

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        // Fetch all messages
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        // Fetch message by ID
        Message message = messageService.getMessageById(messageId);

        if (message == null) {
            return ResponseEntity.status(200).body(null);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        // Delete the message
        Message deletedMessage = messageService.deleteMessage(messageId);

        if (deletedMessage == null) {
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

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

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccountId(@PathVariable Integer accountId) {
        // Fetch messages by account ID
        List<Message> messages = messageService.getMessagesByAccountId(accountId);

        return ResponseEntity.ok(messages);
    }
}
