package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.DeliveryPartner;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;
import com.itsjaypatel.zomatoapp.entities.Restaurant;

import java.util.List;

public interface OrderService {

    OrderEntity findById(Long id);

    OrderEntity save(OrderEntity orderEntity);

    List<OrderEntity> findByCustomer(Customer customer);

    List<OrderEntity> findByDeliveryPartner(DeliveryPartner deliveryPartner);

    List<OrderEntity> findByRestaurant(Restaurant restaurant);


}
