package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(UserEntity user);

    @Query("SELECT w from Wallet w join w.user u join u.roles r where r = 'ADMIN'")
    List<Wallet> findAllWalletsForAdminUsers();
//    List<Wallet> findByUserRoles(Set<Role> roles);

}
