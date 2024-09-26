package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.entities.Wallet;
import com.itsjaypatel.quickbites.entities.WalletTransaction;

import java.util.List;

public interface WalletService {

    Wallet createWallet(UserEntity user);

    Wallet getWalletByUser(UserEntity user);

    Wallet deduct(Wallet wallet, Double amount, String referenceId);

    Wallet credit(Wallet wallet, Double amount, String referenceId);

    List<Wallet> findAdminWallet();

    List<WalletTransaction> getWalletTransactions(Long walletId);
}
