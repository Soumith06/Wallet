package com.WalletManagement.WalletApi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long txnId;
    private String payerPhoneNumber;
    private String payeePhoneNumber;
    private Long txnAmount;
    private String status;

    public Transaction() {
    }

    public Transaction(Long txnId, String payerPhoneNumber, String payeePhoneNumber, Long txnAmount, String status) {
        this.txnId = txnId;
        this.payerPhoneNumber = payerPhoneNumber;
        this.payeePhoneNumber = payeePhoneNumber;
        this.txnAmount = txnAmount;
        this.status = status;
    }
}
