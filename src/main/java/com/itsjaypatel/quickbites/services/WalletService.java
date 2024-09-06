package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.entities.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(UserEntity user);

    Wallet getWalletByUser(UserEntity user);

    Wallet deduct(Wallet wallet,Double amount);

    Wallet credit(Wallet wallet,Double amount);

    List<Wallet> findAdminWallet();
}
