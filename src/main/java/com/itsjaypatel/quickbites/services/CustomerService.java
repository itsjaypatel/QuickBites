package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.dtos.CustomerDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;

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
