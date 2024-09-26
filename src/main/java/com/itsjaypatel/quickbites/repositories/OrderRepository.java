package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {


    List<OrderEntity> findAllByCustomerId(Long customerId);

    List<OrderEntity> findAllByDeliveryPartnerId(Long deliveryPartnerId);

    List<OrderEntity> findAllByRestaurantId(Long restaurantId);


}
