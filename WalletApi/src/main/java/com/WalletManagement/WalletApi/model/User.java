package com.WalletManagement.WalletApi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String name;
    private String mobileNumber;
    private String emailId;
    private String active;

    public User(Long userId, String userName, String name, String mobileNumber, String emailId, String active) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.active = active;
    }

    public User() {
    }
}
