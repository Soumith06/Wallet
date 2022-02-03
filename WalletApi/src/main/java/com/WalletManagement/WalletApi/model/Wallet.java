package com.WalletManagement.WalletApi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    private String mobileNumber;
    private Long balance;

    public Wallet(String mobileNumber, Long balance) {
        this.mobileNumber = mobileNumber;
        this.balance = balance;
    }

    public Wallet() {

    }
}
