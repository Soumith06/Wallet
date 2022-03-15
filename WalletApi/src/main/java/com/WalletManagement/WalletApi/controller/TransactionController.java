package com.WalletManagement.WalletApi.controller;


import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Transaction;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger= LogManager.getLogger(TransactionController.class);

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction){
        try{
            logger.info("transaction started");
            return new ResponseEntity<Transaction>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
        }
        catch (NotFoundException walletNotFoundException){
            logger.error(walletNotFoundException.getMessage());
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions(){
        try{
            logger.info("getting all transactions");
            return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactions(),HttpStatus.OK);
        }
        catch (NotFoundException transactionsNotFoundException){
            logger.error(transactionsNotFoundException.getMessage());
            return new ResponseEntity<String>(transactionsNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllTransactionByUserId(@PathVariable Long userId){
        try{
            logger.info("Getting all transactions of UserId:"+ userId);
            return new ResponseEntity<List<Transaction>>(transactionService.getTransactionByUserId(userId),HttpStatus.OK);
        }
        catch (NotFoundException userNotFoundException){
            logger.error(userNotFoundException.getMessage());
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTransactionByTxnId(@RequestParam(value = "txnId") Long txnId){
        try {
            logger.info("Getting transaction of TxnId:"+ txnId);
            return new ResponseEntity<Transaction>(transactionService.getTransactionByTxnId(txnId),HttpStatus.OK);
        }
        catch (NotFoundException transactionNotFoundException){
            logger.error(transactionNotFoundException.getMessage());
            return new ResponseEntity<String>(transactionNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
