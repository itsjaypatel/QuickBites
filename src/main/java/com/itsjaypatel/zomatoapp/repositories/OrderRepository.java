package com.itsjaypatel.zomatoapp.repositories;

import com.itsjaypatel.zomatoapp.entities.OrderEntity;
import com.itsjaypatel.zomatoapp.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


    List<OrderEntity> findAllByCustomerId(Long customerId);

    List<OrderEntity> findAllByDeliveryPartnerId(Long deliveryPartnerId);

    List<OrderEntity> findAllByRestaurantId(Long restaurantId);


}
