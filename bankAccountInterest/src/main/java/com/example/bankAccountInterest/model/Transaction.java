package com.example.bankAccountInterest.model;

public class Transaction {
    private String transactionId;
    private String date;
    private String type; // D for deposit, W for withdrawal
    private double amount;
    private double balance;

    public Transaction(String transactionId, String date, String type, double amount) {
        this.transactionId = transactionId;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    // Getters and Setters

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}