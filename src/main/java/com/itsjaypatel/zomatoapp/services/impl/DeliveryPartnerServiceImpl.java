package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.dtos.DeliveryPartnerDto;
import com.itsjaypatel.zomatoapp.dtos.DeliveryPartnerOrderUpdateDto;
import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.entities.*;
import com.itsjaypatel.zomatoapp.exceptions.BadRequestException;
import com.itsjaypatel.zomatoapp.exceptions.ResourceConflictException;
import com.itsjaypatel.zomatoapp.exceptions.ResourceNotFoundException;
import com.itsjaypatel.zomatoapp.repositories.*;
import com.itsjaypatel.zomatoapp.services.DeliveryPartnerService;
import com.itsjaypatel.zomatoapp.services.OrderService;
import com.itsjaypatel.zomatoapp.services.UserService;
import com.itsjaypatel.zomatoapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final UserService userService;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantRatingRepository restaurantRatingRepository;
    private final CustomerRatingRepository customerRatingRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final WalletService walletService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final RestaurantPartnerRepository restaurantPartnerRepository;

    @Override
    public DeliveryPartnerDto getMyProfile() {
        return modelMapper.map(currentLoggedDeliveryPartner(), DeliveryPartnerDto.class);
    }

    @Override
    public List<OrderDto> viewOrders() {
        DeliveryPartner currentLoggedDeliveryPartner = this.currentLoggedDeliveryPartner();
        List<OrderDto> orders = orderService
                .findByDeliveryPartner(currentLoggedDeliveryPartner)
                .stream()
                .map(o -> modelMapper.map(o,OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public void rateCustomer(Long customerId, Integer rating) {
        UserEntity me = userService.currentLoggedUser();
        Customer customer = this
                .customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not found with id " + customerId));
        UserEntity customerUser = customer.getUser();
        customerRatingRepository
                .save(new CustomerRating(me,customerUser,rating));

        Double updatedAverageRating = customerRatingRepository.averageRatingForCustomer(customerUser.getId());
        customer.setRating(updatedAverageRating);

        customerRepository.save(customer);
    }

    @Override
    public void rateRestaurant(Long restaurantId, Integer rating) {
        UserEntity user = userService.currentLoggedUser();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurantRatingRepository.save(new RestaurantRating(user,restaurant,rating));
        Double updatedAverageRating = restaurantRatingRepository.averageRatingForRestaurant(restaurant.getId());

        restaurant.setRating(updatedAverageRating);
        restaurantRepository.save(restaurant);
    }

    @Override
    public OrderDto acceptOrder(OrderDto orderDto){
        DeliveryPartner deliveryPartner = currentLoggedDeliveryPartner();
        Wallet deliveryPartnerWallet = walletService.getWalletByUser(deliveryPartner.getUser());

        OrderEntity order = orderService.findById(orderDto.getId());
        if(order.getDeliveryPartner() != null){
            throw new ResourceConflictException("Delivery partner already assigned to this order");
        }
        order.setDeliveryPartner(deliveryPartner);
        return modelMapper.map(orderService.save(order),OrderDto.class);
    }

    @Override
    public OrderDto updateOrderStatus(DeliveryPartnerOrderUpdateDto updateDto) {
        DeliveryPartner deliveryPartner = currentLoggedDeliveryPartner();
        OrderEntity order = orderService.findById(updateDto.getOrderId());
        if(!order.getDeliveryPartner().equals(deliveryPartner)){
            throw new ResourceConflictException("Delivery partner with id " + deliveryPartner.getId() + " is not assigned to this order");
        }

        if(order.getOrderStatus().equals(OrderStatus.READY) && updateDto.getOrderStatus().equals(OrderStatus.PICKED_FROM_RESTAURANT)
        && order.getRestaurantPartnerOtp().equals(updateDto.getRestaurantPartnerOtp())){
            order.setOrderStatus(OrderStatus.PICKED_FROM_RESTAURANT);
        }else if(order.getOrderStatus().equals(OrderStatus.PICKED_FROM_RESTAURANT)
        && updateDto.getOrderStatus().equals(OrderStatus.ON_THE_WAY)){
            order.setOrderStatus(OrderStatus.ON_THE_WAY);
        }else if(order.getOrderStatus().equals(OrderStatus.ON_THE_WAY)
        && updateDto.getOrderStatus().equals(OrderStatus.DELIVERED) && order.getCustomerOtp().equals(updateDto.getRestaurantPartnerOtp())){
            order.setOrderStatus(OrderStatus.DELIVERED);

            RestaurantPartner restaurantPartner = restaurantPartnerRepository.findByRestaurantId(order.getRestaurant().getId()).get();

            Wallet restaurantPartnerWallet =  walletService.getWalletByUser(restaurantPartner.getUser());
            Wallet deliveryPartnerWallet = walletService.getWalletByUser(deliveryPartner.getUser());
            List<Wallet> adminWalletList = walletService.findAdminWallet();

            Double restaurantPartnerShare = order.getAmount()*0.7;
            Double deliveryPartnerShare = order.getAmount()*0.15;
            walletService.credit(restaurantPartnerWallet, restaurantPartnerShare);
            walletService.credit(deliveryPartnerWallet, deliveryPartnerShare);
            adminWalletList.forEach(adminWallet -> walletService.deduct(adminWallet,order.getAmount() - (restaurantPartnerShare + deliveryPartnerShare)));

        }else{
            throw new BadRequestException("You are not authorized to update this order");
        }
        return modelMapper.map(orderService.save(order),OrderDto.class);
    }


    private DeliveryPartner currentLoggedDeliveryPartner(){
        UserEntity user = userService.currentLoggedUser();
        return deliveryPartnerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not associated with user with id " + user.getId()));
    }



}
