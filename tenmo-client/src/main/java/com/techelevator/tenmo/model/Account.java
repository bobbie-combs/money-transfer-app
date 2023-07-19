package com.techelevator.tenmo.model;

import java.math.BigDecimal;


/**
 * The Account class represents a user account in the Tenmo application.
 * It stores information such as the user ID, account ID, and account balance.
 */
public class Account {
    private int userId; // The ID of the user associated with this account
    private int accountId; // The unique ID of the account
    private BigDecimal balance; // The current balance of the account

    /**
     * Default constructor for the Account class.
     */
    public Account() {

    }

    /**
     * Constructs an Account object with the provided user ID, account ID, and initial balance.
     * @param userId    The ID of the user associated with this account.
     * @param accountId The unique ID of the account.
     * @param balance   The initial balance of the account.
     */
    public Account(int userId, int accountId, BigDecimal balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

    /**
     * Retrieves the user ID associated with this account.
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with this account.
     * @param userId The user ID to set.
     */
    public void setUser(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the account ID.
     * @return The account ID.
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Sets the account ID.
     * @param accountId The account ID to set.
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Retrieves the current balance of the account.
     * @return The account balance.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     * @param balance The balance to set.
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}