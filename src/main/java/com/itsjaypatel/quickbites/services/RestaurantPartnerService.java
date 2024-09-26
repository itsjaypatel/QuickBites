package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;

import java.util.List;

public interface RestaurantPartnerService {

    RestaurantPartnerDto getMyProfile();

    List<OrderDto> viewOrders();

    OrderDto acceptOrder(String orderId);

    OrderDto rejectOrder(String orderId);

    FoodItemDto addFoodItem(FoodItemDto foodItemDto);

    FoodItemDto removeFoodItem(Long id);
}
