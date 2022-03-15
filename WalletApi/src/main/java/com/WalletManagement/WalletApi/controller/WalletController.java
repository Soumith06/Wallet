package com.WalletManagement.WalletApi.controller;

import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger= LogManager.getLogger(WalletController.class);

    @PostMapping
    public @ResponseBody ResponseEntity<?> createWallet(@RequestBody Wallet wallet){
        try{
            logger.info("Creating Wallet of MobileNumber:"+wallet.getMobileNumber());
            return new ResponseEntity<Wallet>(walletService.createWallet(wallet),HttpStatus.CREATED);
        }
        catch (NotFoundException userNotFoundException){
            logger.error(userNotFoundException.getMessage());
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllWallets(){
        try{
            logger.info("Getting all wallets");
            return new ResponseEntity<List<Wallet>>(walletService.getAllWallets(),HttpStatus.OK);
        }
        catch (NotFoundException walletsNotFoundException){
            logger.error(walletsNotFoundException.getMessage());
            return new ResponseEntity<String>(walletsNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<?> getWalletById(@PathVariable String mobileNumber){
        try{
            logger.info("Getting wallet of mobileNumber:"+mobileNumber);
            return new ResponseEntity<Wallet>(walletService.getWalletById(mobileNumber),HttpStatus.OK);
        }
        catch (NotFoundException walletNotFoundException){
            logger.error(walletNotFoundException.getMessage());
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<String> deleteWallet(@PathVariable String mobileNumber){
        try{
            logger.info("Deleting wallet of mobileNumber:"+mobileNumber);
            walletService.deleteWalletById(mobileNumber);
            return new ResponseEntity<String>("Wallet deleted",HttpStatus.ACCEPTED);
        }
        catch (NotFoundException walletNotFoundException){
            logger.error(walletNotFoundException.getMessage());
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{mobileNumber}/{money}")
    public ResponseEntity<?> addMoney(@PathVariable Long money,@PathVariable String mobileNumber){
        try{
            logger.info("Adding "+ money + "to wallet of mobileNumber:" + mobileNumber);
            return new ResponseEntity<Wallet>(walletService.addMoney(money,mobileNumber),HttpStatus.CREATED);
        }
        catch (NotFoundException walletNotFoundException){
            logger.error(walletNotFoundException.getMessage());
            return new ResponseEntity<String>(walletNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
