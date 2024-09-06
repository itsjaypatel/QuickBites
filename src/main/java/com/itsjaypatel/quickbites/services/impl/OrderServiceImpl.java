package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.OrderEntity;
import com.itsjaypatel.quickbites.entities.Restaurant;
import com.itsjaypatel.quickbites.services.OrderService;
import com.itsjaypatel.quickbites.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public OrderEntity findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderEntity> findByCustomer(Customer customer) {
        return orderRepository.findAllByCustomerId(customer.getId());
    }


    @Override
    public List<OrderEntity> findByDeliveryPartner(DeliveryPartner deliveryPartner) {
        return orderRepository.findAllByDeliveryPartnerId(deliveryPartner.getId());
    }


    @Override
    public List<OrderEntity> findByRestaurant(Restaurant restaurant) {
        return orderRepository.findAllByRestaurantId(restaurant.getId());
    }


}
