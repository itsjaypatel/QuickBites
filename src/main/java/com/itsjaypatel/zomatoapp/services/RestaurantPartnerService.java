package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.FoodItemDto;
import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.dtos.RestaurantPartnerDto;
import com.itsjaypatel.zomatoapp.entities.RestaurantPartner;

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
