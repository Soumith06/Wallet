package com.WalletManagement.WalletApi.model;

import com.WalletManagement.WalletApi.Utils.enums.WalletStatus;
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

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String mobileNumber;

    @Column(unique = true,nullable = false)
    private String emailId;

    private WalletStatus active;

    public User(Long userId, String userName, String name, String mobileNumber, String emailId, WalletStatus active) {
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
