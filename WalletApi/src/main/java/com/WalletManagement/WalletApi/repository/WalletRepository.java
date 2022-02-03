package com.WalletManagement.WalletApi.repository;

import com.WalletManagement.WalletApi.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,String> {
}
