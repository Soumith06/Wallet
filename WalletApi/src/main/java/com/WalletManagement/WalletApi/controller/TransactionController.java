package com.WalletManagement.WalletApi.controller;


import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Transaction;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction){
        try{
            return new ResponseEntity<Transaction>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
        }
        catch (NotFoundException walletNotFoundException){
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions(){
        try{
            return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactions(),HttpStatus.ACCEPTED);
        }
        catch (NotFoundException transactionNotFoundException){
            return new ResponseEntity<String>(transactionNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllTransactionByUserId(@PathVariable Long userId){
        try{
            return new ResponseEntity<List<Transaction>>(transactionService.getTransactionByUserId(userId),HttpStatus.OK);
        }
        catch (NotFoundException userNotFoundException){
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTransactionByTxnId(@RequestParam(value = "txnId") Long txnId){
        try {
            return new ResponseEntity<Transaction>(transactionService.getTransactionByTxnId(txnId),HttpStatus.OK);
        }
        catch (NotFoundException transactionNotFoundException){
            return new ResponseEntity<String>(transactionNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
