package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.entities.Wallet;
import com.itsjaypatel.quickbites.entities.WalletTransaction;
import com.itsjaypatel.quickbites.enums.TransactionType;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.WalletRepository;
import com.itsjaypatel.quickbites.repositories.WalletTransactionRepository;
import com.itsjaypatel.quickbites.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;


    @Override
    public Wallet createWallet(UserEntity user) {
        return walletRepository.save(Wallet.builder().balance(0.0).user(user).build());
    }

    @Override
    public Wallet getWalletByUser(UserEntity user) {
        return walletRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Wallet not associated for user with id " + user.getId())
        );
    }

    @Override
    public Wallet deduct(Wallet wallet, Double amount, String referenceId) {
        if(wallet.getBalance() < amount) {
            throw new BadRequestException("Transaction initiation failed due to insufficient funds");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        Wallet updatedWallet = walletRepository.save(wallet);
        createTransaction(WalletTransaction
                .builder()
                .amount(amount)
                .description("Debit Transaction")
                .type(TransactionType.DEBIT)
                .paymentId(referenceId)
                .wallet(updatedWallet)
                .build());
        return updatedWallet;
    }

    @Override
    public Wallet credit(Wallet wallet, Double amount, String referenceId) {
        wallet.setBalance(wallet.getBalance() + amount);
        Wallet updatedWallet = walletRepository.save(wallet);
        createTransaction(WalletTransaction
                .builder()
                .amount(amount)
                .description("Credit Transaction")
                .type(TransactionType.CREDIT)
                .paymentId(referenceId)
                .wallet(updatedWallet)
                .build());
        return updatedWallet;
    }

    @Override
    public List<Wallet> findAdminWallet() {
        return walletRepository.findAllWalletsForAdminUsers();
    }

    @Override
    public List<WalletTransaction> getWalletTransactions(Long walletId) {
        return walletTransactionRepository.findAllByWalletId(walletId);
    }


    private void createTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

}
