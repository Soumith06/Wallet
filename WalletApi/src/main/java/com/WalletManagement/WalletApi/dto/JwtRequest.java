package com.WalletManagement.WalletApi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    private String userName;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}


