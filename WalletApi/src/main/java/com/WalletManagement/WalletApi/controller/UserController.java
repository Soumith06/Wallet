package com.WalletManagement.WalletApi.controller;

import com.WalletManagement.WalletApi.exceptions.AlreadyExistsException;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User newUser=userService.addUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
        }
        catch (AlreadyExistsException userAlreadyExistsException){
            return new ResponseEntity<String>(userAlreadyExistsException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
        }
        catch (NotFoundException userNotFoundException){
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            return new ResponseEntity<User>(userService.getUserById(id),HttpStatus.OK);
        }
        catch (NotFoundException userNotFoundException){
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id ){
        try{
            userService.deleteUserById(id);
            return new ResponseEntity<String>("User Deleted",HttpStatus.ACCEPTED);
        }
        catch (NotFoundException userNotFoundException){
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

}
