package com.WalletManagement.WalletApi.repository;

import com.WalletManagement.WalletApi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
