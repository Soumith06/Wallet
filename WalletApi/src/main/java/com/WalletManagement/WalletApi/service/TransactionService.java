package com.WalletManagement.WalletApi.service;

import com.WalletManagement.WalletApi.Utils.enums.TransactionStatus;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Transaction;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.TransactionRepository;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction createTransaction(Transaction transaction){

        if(walletRepository.findById(transaction.getPayerPhoneNumber()).isEmpty()){
            throw new NotFoundException("Payer Phone Number doesn't Exists");
        }
        else if(walletRepository.findById(transaction.getPayeePhoneNumber()).isEmpty()){
            throw new NotFoundException("Payee Phone Number doesn't Exists");
        }
        else {
            Wallet payerWallet=walletRepository.findById(transaction.getPayerPhoneNumber()).get();
            Wallet payeeWallet=walletRepository.findById(transaction.getPayeePhoneNumber()).get();

            if(payerWallet.getBalance()>=transaction.getTxnAmount()){

                payeeWallet.setBalance(payeeWallet.getBalance()+transaction.getTxnAmount());
                walletRepository.save(payeeWallet);

                payerWallet.setBalance(payerWallet.getBalance()-transaction.getTxnAmount());
                walletRepository.save(payerWallet);

                transaction.setStatus(TransactionStatus.Successful);
                return transactionRepository.save(transaction);
            }
            else{
                transaction.setStatus(TransactionStatus.Failed);
                return transactionRepository.save(transaction);
            }
        }

    }

    public List<Transaction> getAllTransactions() {
        if(transactionRepository.findAll().isEmpty()){
            throw new NotFoundException("Transactions List is empty");
        }
        else{
            return transactionRepository.findAll();
        }
    }

    public List<Transaction> getTransactionByUserId(Long userId) {
        if(!userRepository.findById(userId).isEmpty()){
            String userMobileNumber=userRepository.findById(userId).get().getMobileNumber();

            List<Transaction> transactionsByUserId=new ArrayList<Transaction>();
            List<Transaction> transactionList=transactionRepository.findAll();

            for(Transaction transaction:transactionList){
                if(transaction.getPayerPhoneNumber().equals(userMobileNumber) || transaction.getPayeePhoneNumber().equals(userMobileNumber)){
                    transactionsByUserId.add(transaction);
                }
            }
            if(transactionsByUserId.isEmpty()){
                throw new NotFoundException("No Transactions");
            }

            return transactionsByUserId;
        }
        else {
            throw new NotFoundException("UserId doesn't exists");
        }
    }

    public Transaction getTransactionByTxnId(Long txnId) {
         if(transactionRepository.findById(txnId).isEmpty()){
             throw new NotFoundException("Transaction doesn't exist");
         }
         else {
             return transactionRepository.findById(txnId).get();
         }
    }
}
