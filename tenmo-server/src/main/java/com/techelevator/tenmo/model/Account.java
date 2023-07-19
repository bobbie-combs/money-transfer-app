package com.techelevator.tenmo.model;
import com.techelevator.tenmo.model.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Account {
    private int userId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    private BigDecimal balance;

    public Account () {

    }
    public Account(int userId , int accountId, BigDecimal balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public void increaseBalance(BigDecimal balance) {
        this.balance.add(balance);
    }
    public void decreaseBalance(BigDecimal balance) {
        this.balance.subtract(balance);
    }
}
