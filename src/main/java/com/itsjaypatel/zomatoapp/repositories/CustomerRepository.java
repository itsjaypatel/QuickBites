package com.itsjaypatel.zomatoapp.repositories;

import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser(UserEntity user);
}
