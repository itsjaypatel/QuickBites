package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.DeliveryPartner;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;
import com.itsjaypatel.zomatoapp.entities.Restaurant;
import com.itsjaypatel.zomatoapp.repositories.OrderRepository;
import com.itsjaypatel.zomatoapp.services.OrderService;
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
