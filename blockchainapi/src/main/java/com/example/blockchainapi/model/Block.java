package com.example.blockchainapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Block {
    private int index;
    private long timestamp;
    private List<Transaction> transactions;
    private String previousHash;

    private String hash;

    private int nonce;

    public Block() {}

    public Block(int index, long timestamp, List<Transaction> transactions, String previousHash, String hash, int nonce) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = hash;
        this.nonce = nonce;
    }

    public int getIndex(){return index;}
    public void setIndex(int index){this.index = index;}

    public long getTimestamp(){return timestamp;}
    public void setTimestamp(long timestamp){this.timestamp = timestamp;}

    public List<Transaction> getTransactions(){return transactions;}
    public void setTransactions(List<Transaction> transactions){this.transactions = transactions;}

    public String getPreviousHash(){return previousHash;}
    public void setPreviousHash(String previousHash){this.previousHash = previousHash;}

    public String getHash(){return hash;}
    public void setHash(String hash){this.hash = hash;}

    public int getNonce(){return nonce;}
    public void setNonce(int nonce){this.nonce = nonce;}


}
