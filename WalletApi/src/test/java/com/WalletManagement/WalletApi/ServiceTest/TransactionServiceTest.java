package com.WalletManagement.WalletApi.ServiceTest;

import com.WalletManagement.WalletApi.Utils.enums.TransactionStatus;
import com.WalletManagement.WalletApi.Utils.enums.WalletStatus;
import com.WalletManagement.WalletApi.model.Transaction;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.TransactionRepository;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.repository.WalletRepository;
import com.WalletManagement.WalletApi.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void createSuccessfulTransactionTest(){
        Wallet payerWallet =new Wallet("00",100L);
        Wallet payeeWallet =new Wallet("01",100L);
        Transaction transaction=new Transaction(0L,"00","01",50L, TransactionStatus.Successful);
        Mockito.when(walletRepository.findById("00")).thenReturn(Optional.of(payerWallet));
        Mockito.when(walletRepository.findById("01")).thenReturn(Optional.of(payeeWallet));
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Assertions.assertEquals(TransactionStatus.Successful,transactionService.createTransaction(transaction).getStatus());
    }

    @Test
    public void createFailedTransactionTest(){
        Wallet payerWallet =new Wallet("00",100L);
        Wallet payeeWallet =new Wallet("01",100L);
        Transaction transaction=new Transaction(0L,"00","01",150L,TransactionStatus.Successful);
        Mockito.when(walletRepository.findById("00")).thenReturn(Optional.of(payerWallet));
        Mockito.when(walletRepository.findById("01")).thenReturn(Optional.of(payeeWallet));
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Assertions.assertEquals(TransactionStatus.Failed,transactionService.createTransaction(transaction).getStatus());
    }

    @Test
    public void getAllTransactionsTest(){
        List<Transaction> transactionList= Stream
                .of(new Transaction(0L,"00","01",50L,TransactionStatus.Successful),new Transaction(1L,"00","01",150L,TransactionStatus.Failed))
                .collect(Collectors.toList());
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);
        Assertions.assertEquals(transactionList,transactionService.getAllTransactions());
    }

    @Test
    public void getTransactionByTxnIdTest(){
        Transaction transaction=new Transaction(0L,"00","01",50L,TransactionStatus.Successful);
        Mockito.when(transactionRepository.findById(0L)).thenReturn(Optional.of(transaction));
        Assertions.assertEquals(transaction,transactionService.getTransactionByTxnId(0L));
    }

    @Test
    public void getTransactionByUserIdTest(){
        User user=new User(0L,"sou06","sou","00","g@gmail.com", WalletStatus.False);
        List<Transaction> transactionList= Stream
                .of(new Transaction(0L,"00","01",50L,TransactionStatus.Successful)
                        ,new Transaction(1L,"01","00",150L,TransactionStatus.Failed),new Transaction(2L,"02","01",150L,TransactionStatus.Failed))
                .collect(Collectors.toList());
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);
        Assertions.assertEquals(2,transactionService.getTransactionByUserId(0L).size());
    }
}
