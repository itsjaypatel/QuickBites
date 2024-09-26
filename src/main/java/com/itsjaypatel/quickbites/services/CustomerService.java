package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.CartDto;
import com.itsjaypatel.quickbites.dtos.CustomerDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.WalletDto;

import java.util.List;

public interface CustomerService {

    CustomerDto getProfile();

    WalletDto getWallet();

    List<OrderDto> viewOrders();

    void rateDeliveryPartner(Long deliveryPartnerId, Integer rating);

    void rateFood(Long foodItemId, Integer rating);

    void rateRestaurant(Long restaurantId, Integer rating);

    void addToFavourites(Long foodItemId);

    void removeFromFavourites(Long foodItemId);

    CartDto addToCart(Long foodItemId);

    CartDto removeFromCart(Long foodItemId);
}
