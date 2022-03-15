package com.WalletManagement.WalletApi.controller;

import com.WalletManagement.WalletApi.dto.JwtRequest;
import com.WalletManagement.WalletApi.security.JwtUtil;
import com.WalletManagement.WalletApi.service.MyUserDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    private static Logger logger=LogManager.getLogger(JwtController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest){
        try {
            logger.info("Authenticating user");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            logger.error("Invalid username or password");
            return new ResponseEntity<String>("Invalid username or password",HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = myUserDetailService.loadUserByUsername(jwtRequest.getUserName());
        String token = jwtUtil.generateToken(userDetails);
        logger.info("jwtToken :"+token);
        return new ResponseEntity<String>("jwtToken :"+token, HttpStatus.OK);
    }
}
