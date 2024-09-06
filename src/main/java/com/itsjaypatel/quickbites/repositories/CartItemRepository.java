package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.CartItem;
import com.itsjaypatel.quickbites.entities.compositekeys.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {

    Optional<CartItem> findByCustomerIdAndFoodItemId(Long customerId, Long foodItemId);
}
