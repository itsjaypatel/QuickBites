package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser(UserEntity user);
}
