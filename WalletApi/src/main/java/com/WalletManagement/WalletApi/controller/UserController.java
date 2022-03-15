package com.WalletManagement.WalletApi.controller;

import com.WalletManagement.WalletApi.exceptions.AlreadyExistsException;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger= LogManager.getLogger(UserController.class);

    @PostMapping("/createNewUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            logger.info("Creating User for User Detsils"+ user.toString());
            User newUser=userService.addUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
        }
        catch (AlreadyExistsException userAlreadyExistsException){
            logger.error(userAlreadyExistsException.getMessage());
            return new ResponseEntity<String>(userAlreadyExistsException.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            logger.info("Getting all users");
            return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
        }
        catch (NotFoundException usersNotFoundException){
            logger.error(usersNotFoundException.getMessage());
            return new ResponseEntity<String>(usersNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            logger.info("Getting user of userId:"+id);
            return new ResponseEntity<User>(userService.getUserById(id),HttpStatus.OK);
        }
        catch (NotFoundException userNotFoundException){
            logger.error(userNotFoundException.getMessage());
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id ){
        try{
            logger.info("Deleting User of userId:"+id);
            userService.deleteUserById(id);
            return new ResponseEntity<String>("User Deleted",HttpStatus.ACCEPTED);
        }
        catch (NotFoundException userNotFoundException){
            logger.error(userNotFoundException.getMessage());
            return new ResponseEntity<String>(userNotFoundException.getMessage(),HttpStatus.CONFLICT);
        }
    }

}
