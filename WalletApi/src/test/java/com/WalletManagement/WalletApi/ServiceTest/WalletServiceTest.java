package com.WalletManagement.WalletApi.ServiceTest;

import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.repository.WalletRepository;
import com.WalletManagement.WalletApi.service.WalletService;
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
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void createWalletTest(){
        User user=new User(0L,"sou06","sou","00","g@gmail.com","false");
        Wallet wallet=new Wallet("00",100L);
        Mockito.when(userRepository.findByMobileNumber("00")).thenReturn(user);
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
        Assertions.assertEquals(wallet,walletService.createWallet(wallet));
    }

    @Test
    public void getAllWalletsTest(){
        List<Wallet> walletList=Stream.of(new Wallet("00",100L),new Wallet("10",200L)).collect(Collectors.toList());
        Mockito.when(walletRepository.findAll()).thenReturn(walletList);
        Assertions.assertEquals(walletList,walletService.getAllWallets());
    }

    @Test
    public void deleteWalletByIdTest(){
        Wallet wallet=new Wallet("00",100L);
        User user=new User(0L,"sou06","sou","00","g@gmail.com","false");
        Mockito.when(walletRepository.findById("00")).thenReturn(Optional.of(wallet));
        Mockito.when(userRepository.findByMobileNumber("00")).thenReturn(user);
        walletService.deleteWalletById("00");
        Mockito.verify(walletRepository,Mockito.times(1)).deleteById("00");
    }

    @Test
    public void getWalletByIdTest() {
        Wallet wallet=new Wallet("00", 100L);
        Mockito.when(walletRepository.findById("00")).thenReturn(Optional.of(wallet));
        Assertions.assertEquals(wallet, walletService.getWalletById("00"));
    }

    @Test
    public void addMoneyTest(){
        Wallet wallet=new Wallet("00", 100L);
        Mockito.when(walletRepository.findById("00")).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(wallet)).thenReturn(new Wallet("00", 150L));
        Assertions.assertEquals(150L,walletService.addMoney(50L,"00").getBalance());
    }
}
