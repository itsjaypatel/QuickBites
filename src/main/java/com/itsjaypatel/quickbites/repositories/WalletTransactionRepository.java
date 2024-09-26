package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findAllByWalletId(Long walletId);
}
