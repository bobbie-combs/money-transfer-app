package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @RestController
    @PreAuthorize("isAuthenticated")
    public class AccountController {
        @Autowired
        private AccountRepository accountRepository;

        @GetMapping("/accounts/findByUserId/{userId}")
        @PreAuthorize("permitAll")
        public Account findByName(@PathVariable int userId) {
            return accountRepository.findByUserId(userId);
        }

        @GetMapping("/accounts/{id}")
        public Account findById(@PathVariable int accountId) {
        return accountRepository.findByUserId(accountId);
        }

        @GetMapping("/accounts")
        @PreAuthorize("permitAll")
        public List<Account> findAllAccounts() {
            return accountRepository.findAll();
        }

}
