package com.WalletManagement.WalletApi.service;

import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;

import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean validateWallet(Wallet wallet) {
        List<Wallet> walletsList=walletRepository.findAll();
        for (Wallet exisitingWallet:walletsList){
            if(exisitingWallet.getMobileNumber().equals(wallet.getMobileNumber()))
                return false;
        }
        return true;
    }

    public Wallet createWallet(Wallet wallet){
        User user=userRepository.findByMobileNumber(wallet.getMobileNumber());
        if(user != null){
            if(wallet.getBalance()==null){
                wallet.setBalance(0L);
            }
            Wallet newWallet=walletRepository.save(wallet);
            user.setActive("true");
            userRepository.save(user);
            return newWallet;
        }
        else {
            throw new NotFoundException("Create user first");
        }
    }

    public List<Wallet> getAllWallets() {
        List<Wallet> walletsList=walletRepository.findAll();
        if(walletsList.isEmpty()){
            throw new NotFoundException("Wallets List Empty");
        }
        else{
            return walletsList;
        }
    }

    public void deleteWalletById(String mobileNumber) {

        if (walletRepository.findById(mobileNumber).isEmpty()) {
            throw new NotFoundException("Wallet doesn't Exists");
        }
        else{
            User user = userRepository.findByMobileNumber(mobileNumber);
            user.setActive("false");
            userRepository.save(user);
            walletRepository.deleteById(mobileNumber);
        }
    }
    public Wallet getWalletById(String mobileNumber){
        if (walletRepository.findById(mobileNumber).isEmpty()) {
            throw new NotFoundException("Wallet doesn't Exists");
        } else {
            return walletRepository.findById(mobileNumber).get();
        }
    }
    public Wallet addMoney(Long money,String mobileNumber){
        if(walletRepository.findById(mobileNumber).isEmpty()){
            throw new NotFoundException("Wallet doesn't Exists");
        }
        else {
            Wallet wallet=walletRepository.findById(mobileNumber).get();
            wallet.setBalance(wallet.getBalance()+money);
            return walletRepository.save(wallet);
        }
    }
}
