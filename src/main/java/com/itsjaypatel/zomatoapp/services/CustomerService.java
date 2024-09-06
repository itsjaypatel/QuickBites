package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.CustomerDto;
import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.dtos.UserEntityDto;
import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;

import java.util.List;

public interface CustomerService {

    CustomerDto getProfile();

    List<OrderDto> viewOrders();

    void rateDeliveryPartner(Long deliveryPartnerId,Integer rating);

    void rateFood(Long foodItemId,Integer rating);

    void rateRestaurant(Long restaurantId,Integer rating);

    void addToFavourites(Long foodItemId);

    void removeFromFavourites(Long foodItemId);

    void addToCart(Long foodItemId);

    void removeFromCart(Long foodItemId);

    Customer currentLoggedCustomer();
}
