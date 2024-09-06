package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.OrderEntity;
import com.itsjaypatel.quickbites.entities.Restaurant;

import java.util.List;

public interface OrderService {

    OrderEntity findById(Long id);

    OrderEntity save(OrderEntity orderEntity);

    List<OrderEntity> findByCustomer(Customer customer);

    List<OrderEntity> findByDeliveryPartner(DeliveryPartner deliveryPartner);

    List<OrderEntity> findByRestaurant(Restaurant restaurant);


}
