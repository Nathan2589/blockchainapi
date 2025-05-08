package com.example.blockchainapi.controller;

import com.example.blockchainapi.model.Block;
import com.example.blockchainapi.model.Transaction;
import com.example.blockchainapi.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//tells Spring this class handles HTTP requests and returns data (not views).
//automatically converts Java objects to JSON in responses.
@RestController

//sets base url for all endpoints of the controller
@RequestMapping("/api/blockchain")
public class BlockchainController {
    private final BlockchainService blockchainService;

    //autowired injects the blockchain service in order to use the logic without having to create it
    @Autowired
    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    // get entire blockchain
    @GetMapping("/chain")
    public List<Block> getBlockchain() {
        return blockchainService.getBlockchain();
    }

    // add a new transaction
    @PostMapping("/transaction")
    public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) { //request body converts the JSON to a java object
        blockchainService.addTransaction(transaction);
        return ResponseEntity.ok("Transaction added");
    }

    @PostMapping("/mine")
    public Block mineBlock(){
        return blockchainService.mineBlock();
    }

    //check if blockchain is valid
    @GetMapping("/validate")
    public ResponseEntity<String> validateChain() {
        boolean isValid = blockchainService.isChainValid();
        if (isValid) {
            return ResponseEntity.ok("Chain is valid");
        }
        else{
            return ResponseEntity.ok("Chain is not valid");
        }
    }


}
