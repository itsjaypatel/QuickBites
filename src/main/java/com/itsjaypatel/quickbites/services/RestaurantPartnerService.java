package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;
import com.itsjaypatel.quickbites.entities.RestaurantPartner;

import java.util.List;

public interface RestaurantPartnerService {

//    RestaurantDto addRestaurant(RestaurantDto restaurantDto);
//
//    RestaurantDto removeRestaurant(Long id);
//
//    List<RestaurantDto> getMyRestaurants();

//    RestaurantDto updateRestaurant(RestaurantDto restaurantDto);

    RestaurantPartnerDto getMyProfile();

    List<OrderDto> viewOrders();

    OrderDto acceptOrder(OrderDto orderDto);

    OrderDto rejectOrder(OrderDto orderDto);

    OrderDto readyOrder(OrderDto orderDto);

    RestaurantPartner currentLoggedRestaurantPartner();

    FoodItemDto addFoodItem(FoodItemDto foodItemDto);

    FoodItemDto removeFoodItem(Long id);
}
