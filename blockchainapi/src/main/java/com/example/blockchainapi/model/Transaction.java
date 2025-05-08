package com.example.blockchainapi.model;

public class Transaction {
    private String sender;
    private String receiver;
    private double amount;

    public Transaction() {}

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    // Getters and setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
