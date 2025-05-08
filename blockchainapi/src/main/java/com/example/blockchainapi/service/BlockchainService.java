package com.example.blockchainapi.service;

import com.example.blockchainapi.model.Block;
import com.example.blockchainapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockchainService {

    private List<Block> blockchain;
    private List<Transaction> pendingTransactions;


    public BlockchainService() {
        blockchain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();

        // create the genesis block (first block in the chain)
        createGenesisBlock();
    }

    // create the first block in the blockchain
    private void createGenesisBlock() {
        Block genesisBlock = new Block(
                0,                          // index
                new Date().getTime(),       // timestamp
                new ArrayList<>(),          // empty transaction list
                "0",                        // previous hash (0 for genesis block)
                "",                         // hash will be calculated
                0                           // nonce
        );

        // calculate the hash for the genesis block
        genesisBlock.setHash(calculateHash(genesisBlock));

        // add genesis block to the blockchain
        blockchain.add(genesisBlock);
    }

    // Get the latest block in the chain
    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    // add a new transaction to the pending transactions list
    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    // Mine a new block (add pending transactions to the blockchain)
    public Block mineBlock() {
        // Create a new block with pending transactions
        Block newBlock = new Block(
                blockchain.size(),              // index
                new Date().getTime(),           // timestamp
                new ArrayList<>(pendingTransactions), // copy of pending transactions
                getLatestBlock().getHash(),     // previous hash
                "",                             // hash will be calculated
                0                               // initial nonce
        );

        // mine the block (find a valid hash)
        mineBlockWithProofOfWork(newBlock);

        // add the new block to the chain
        blockchain.add(newBlock);

        // clear the pending transactions
        pendingTransactions.clear();

        return newBlock;
    }

    // implement a simple Proof of Work algorithm
    private void mineBlockWithProofOfWork(Block block) {
        String target = "0000"; // Difficulty target (4 leading zeros)

        while (!block.getHash().startsWith(target)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(calculateHash(block));
        }

        System.out.println("Block mined: " + block.getHash());
    }

    // calculate the hash of a block
    private String calculateHash(Block block) {
        String dataToHash = block.getIndex() +
                block.getTimestamp() +
                block.getPreviousHash() +
                block.getNonce();

        // add transaction data to the hash
        for (Transaction tx : block.getTransactions()) {
            dataToHash += tx.getSender() + tx.getReceiver() + tx.getAmount();
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

            // convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // get the entire blockchain
    public List<Block> getBlockchain() {
        return blockchain;
    }

    // validate the blockchain (check if it's tampered with)
    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            // check if the current block's hash is valid
            if (!currentBlock.getHash().equals(calculateHash(currentBlock))) {
                return false;
            }

            // check if the current block points to the correct previous hash
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}
