package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Retrieves an account by its username.
     * 
     * @param username The username of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    Optional<Account> findByUsername(String username);

    /**
     * Retrieves an account by its username and password.
     * 
     * @param username The username of the account to retrieve.
     * @param password The password of the account to retrieve.
     * @return An Optional containing the account if found, or an empty Optional if not found.
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
