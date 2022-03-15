package com.WalletManagement.WalletApi.service;

import com.WalletManagement.WalletApi.Utils.Validations;
import com.WalletManagement.WalletApi.Utils.enums.WalletStatus;
import com.WalletManagement.WalletApi.exceptions.AlreadyExistsException;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validations validations;

    public User addUser(User user) {
        if(validations.validateUser(user)){
            user.setActive(WalletStatus.False);
            return userRepository.save(user);
        }
        else{
            throw new AlreadyExistsException("User Already exists");
        }
    }

    public List<User> getAllUsers()
    {
        List<User> usersList =userRepository.findAll();
        if(usersList.isEmpty()){
            throw new NotFoundException("Users List Empty");
        }
        else{
            return usersList;
        }
    }

    public void deleteUserById(Long id){
        if(userRepository.findById(id).isEmpty()){
            throw new NotFoundException("User Doesn't Exist");
        }
        else{
            userRepository.deleteById(id);
        }
    }

    public User getUserById(Long id) {
        if(userRepository.findById(id).isEmpty()){
            throw new NotFoundException("User Doesn't Exist");
        }
        else{
            return userRepository.findById(id).get();
        }

    }
}
