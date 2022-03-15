package com.WalletManagement.WalletApi.Utils;

import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validations {

    @Autowired
    private UserRepository userRepository;
    public boolean validateUser(User user){
        List<User> usersList= userRepository.findAll();
        for (User exisitingUser:usersList){
            if(exisitingUser.getMobileNumber().equals(user.getMobileNumber()) || exisitingUser.getUserName().equals(user.getUserName()) || exisitingUser.getEmailId().equals(user.getEmailId()))
                return false;
        }
        return true;
    }
}
