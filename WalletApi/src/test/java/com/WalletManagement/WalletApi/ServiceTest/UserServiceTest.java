package com.WalletManagement.WalletApi.ServiceTest;

import com.WalletManagement.WalletApi.Utils.enums.WalletStatus;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.service.UserService;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getAllUsersTest(){
        List<User> userList= Stream.of(new User(0L,"sou06","sou","00","d@gmail.com",WalletStatus.False)).collect(Collectors.toList());
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Assertions.assertEquals(userList,userService.getAllUsers());
    }

    @Test
    public void addUserTest(){
        User user=new User(0L,"sou06","sou","00","d@gmail.com", WalletStatus.False);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(user,userService.addUser(user));
    }


    @Test
    public void deleteUserByIdTest(){
        User user=new User(0L,"sou06","sou","00","d@gmail.com",WalletStatus.False);
        Mockito.when(userRepository.findById(0l)).thenReturn(Optional.of(user));
        userService.deleteUserById(0L);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(0L);
    }

    @Test
    public void getUserByIdTest(){
        User user=new User(0L,"sou06","sou","00","d@gmail.com", WalletStatus.False);
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user,userService.getUserById(0L));
    }
}
