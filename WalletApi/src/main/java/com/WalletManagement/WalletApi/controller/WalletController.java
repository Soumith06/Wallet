package com.WalletManagement.WalletApi.controller;

import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    
    @PostMapping
    public ResponseEntity<?> createWallet(@RequestBody Wallet wallet){
        try{
            return new ResponseEntity<Wallet>(walletService.createWallet(wallet),HttpStatus.CREATED);
        }
        catch (NotFoundException userNotFoundException){
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllWallets(){
        try{
            return new ResponseEntity<List<Wallet>>(walletService.getAllWallets(),HttpStatus.OK);
        }
        catch (NotFoundException WalletsNotFoundException){
            return new ResponseEntity<String>(WalletsNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<?> getWalletById(@PathVariable String mobileNumber){
        try{
            return new ResponseEntity<Wallet>(walletService.getWalletById(mobileNumber),HttpStatus.OK);
        }
        catch (NotFoundException walletNotFoundException){
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<String> deleteWallet(@PathVariable String mobileNumber){
        try{
            walletService.deleteWalletById(mobileNumber);
            return new ResponseEntity<String>("Wallet deleted",HttpStatus.ACCEPTED);
        }
        catch (NotFoundException walletNotFoundException){
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{mobileNumber}/{money}")
    public ResponseEntity<?> addMoney(@PathVariable Long money,@PathVariable String mobileNumber){
        try{
            return new ResponseEntity<Wallet>(walletService.addMoney(money,mobileNumber),HttpStatus.CREATED);
        }
        catch (NotFoundException walletNotFoundException){
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
