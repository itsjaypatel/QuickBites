package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.repositories.OrderItemRepository;
import com.itsjaypatel.quickbites.repositories.OrderRepository;
import com.itsjaypatel.quickbites.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderEntity findById(String id) {
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

    @Override
    public OrderEntity generateOrderFromCart(List<CartItem> cartItems) {
        Customer customer = cartItems.stream()
                .findFirst()
                .map(CartItem::getCustomer)
                .orElse(null);

        final Restaurant restaurant = cartItems
                .stream()
                .findAny()
                .map(CartItem::getFoodItem)
                .map(FoodItem::getRestaurant)
                .orElse(null);

        final OrderEntity order = orderRepository
                .save(OrderEntity
                        .builder()
                        .id("O" + System.currentTimeMillis())
                        .customer(customer)
                        .orderStatus(OrderStatus.PENDING)
                        .restaurant(restaurant)
                        .build());

        Set<OrderItem> orderItems =
                cartItems
                        .stream()
                        .map(cartItem ->
                                OrderItem
                                        .builder()
                                        .foodItem(cartItem.getFoodItem())
                                        .quantity(cartItem.getQuantity())
                                        .order(order)
                                        .build()).collect(Collectors.toSet());

        //removing cart items after checkout

        Double orderAmount = orderItems
                .stream().map(orderItem -> orderItem.getQuantity() * orderItem.getFoodItem().getPrice())
                .reduce(0.0, Double::sum);

        order.setOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);
        order.setAmount(orderAmount);

        return orderRepository.save(order);
    }


}
