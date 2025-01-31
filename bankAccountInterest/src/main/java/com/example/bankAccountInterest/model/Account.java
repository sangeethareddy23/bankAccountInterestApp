package com.example.bankAccountInterest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
    private String accountId;
    private String accountHolderName;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountId, String accountHolderName, double balance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    // Add a transaction to the account
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        if (transaction.getType().equalsIgnoreCase("D")) {
            this.balance += transaction.getAmount();
        } else if (transaction.getType().equalsIgnoreCase("W")) {
            this.balance -= transaction.getAmount();
        }
        transaction.setBalance(this.balance);
    }

    public List<Transaction> getTransactionsForMonth(int year, int month) {
        return transactions.stream()
                .filter(txn -> {
                    String txnDate = txn.getDate(); // Format: YYYYMMDD
                    int txnYear = Integer.parseInt(txnDate.substring(0, 4));
                    int txnMonth = Integer.parseInt(txnDate.substring(4, 6));
                    return txnYear == year && txnMonth == month;
                })
                .collect(Collectors.toList());
    }

    // Getters and Setters
    public String getAccountId() {
        return accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}