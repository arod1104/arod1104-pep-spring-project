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
     * Retrieves an account by its ID.
     * 
     * @param id The ID of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    /**
     * Retrieves an account by its username.
     * 
     * @param username The username of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
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
