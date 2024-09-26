package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.*;

import java.util.List;

public interface OrderService {

    OrderEntity findById(String id);

    OrderEntity save(OrderEntity orderEntity);

    List<OrderEntity> findByCustomer(Customer customer);

    List<OrderEntity> findByDeliveryPartner(DeliveryPartner deliveryPartner);

    List<OrderEntity> findByRestaurant(Restaurant restaurant);

    OrderEntity generateOrderFromCart(List<CartItem> cartItems);

}
