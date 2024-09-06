package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.entities.UserEntity;
import com.itsjaypatel.zomatoapp.entities.Wallet;
import com.itsjaypatel.zomatoapp.exceptions.ResourceNotFoundException;
import com.itsjaypatel.zomatoapp.repositories.WalletRepository;
import com.itsjaypatel.zomatoapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;


    @Override
    public Wallet createWallet(UserEntity user) {
        return walletRepository.save(Wallet.builder().balance(0.0).user(user).build());
    }

    @Override
    public Wallet getWalletByUser(UserEntity user){
        return walletRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Wallet not associated for user with id " + user.getId())
        );
    }

    @Override
    public Wallet deduct(Wallet wallet, Double amount) {
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet credit(Wallet wallet, Double amount) {
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    @Override
    public List<Wallet> findAdminWallet() {
        return walletRepository.findAllWalletsForAdminUsers();
    }


}
