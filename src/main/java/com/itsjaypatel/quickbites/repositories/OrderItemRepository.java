package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.OrderItem;
import com.itsjaypatel.quickbites.entities.compositekeys.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    Optional<OrderItem> findByOrderIdAndFoodItemId(String orderId, Long foodItemId);
}
