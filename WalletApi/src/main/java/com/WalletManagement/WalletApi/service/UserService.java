package com.WalletManagement.WalletApi.service;

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

    public boolean validateUser(User user){
        List<User> usersList= userRepository.findAll();
        for (User exisitingUser:usersList){
            if(exisitingUser.getMobileNumber().equals(user.getMobileNumber()) || exisitingUser.getUserName().equals(user.getUserName()) || exisitingUser.getEmailId().equals(user.getEmailId()))
                return false;
            }
        return true;
    }
    public User addUser(User user) {
        if(validateUser(user)){
            user.setActive("false");
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
