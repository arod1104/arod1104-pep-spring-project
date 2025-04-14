package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Validates the username and password of an account.
     * 
     * @param account The account object containing username and password.
     * @return true if the username and password are valid, false otherwise.
     */
    public boolean isUsernameAndPasswordValid(Account account) {
        // Validate username and password
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return false;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves an account by its ID.
     * 
     * @param id The ID of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    /**
     * Retrieves an account by its username.
     * 
     * @param username The username of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    public Optional<Account> getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /**
     * Retrieves an account by its username and password.
     * 
     * @param account The account object containing username and password.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    public Optional<Account> getAccountByUsernameAndPassword(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    /**
     * Saves a new account or updates an existing account in the database.
     * 
     * @param account The account object to be saved.
     * @return The saved account object.
     */
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
