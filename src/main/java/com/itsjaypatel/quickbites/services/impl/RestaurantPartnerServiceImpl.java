package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;
import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.RestaurantPartnerRepository;
import com.itsjaypatel.quickbites.services.OrderService;
import com.itsjaypatel.quickbites.services.RestaurantPartnerService;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.entities.*;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.services.FoodItemService;
import com.itsjaypatel.quickbites.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    private final OrderService orderService;
    private final UserService userService;
    private final FoodItemService foodItemService;
    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final ModelMapper modelMapper;

//    @Override
//    public RestaurantDto addRestaurant(RestaurantDto restaurantDto) {
//        return null;
//    }
//
//    @Override
//    public RestaurantDto removeRestaurant(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<RestaurantDto> getMyRestaurants() {
//        return List.of();
//    }

//    @Override
//    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto) {
//
//        return null;
//    }

    @Override
    public RestaurantPartnerDto getMyProfile(){
        return modelMapper.map(currentLoggedRestaurantPartner(), RestaurantPartnerDto.class);
    }

    @Override
    public List<OrderDto> viewOrders() {
        RestaurantPartner restaurantPartner = this.currentLoggedRestaurantPartner();
        List<OrderDto> orders = orderService
                .findByRestaurant(restaurantPartner.getRestaurant())
                .stream()
                .map(o -> modelMapper.map(o,OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public OrderDto acceptOrder(OrderDto orderDto) {
        RestaurantPartner restaurantPartner = currentLoggedRestaurantPartner();
        OrderEntity order = orderService.findById(orderDto.getId());
        Restaurant orderRestaurant =  order.getOrderItems().stream()
                .filter(Objects::nonNull)
                .map(orderItem -> orderItem.getFoodItem().getRestaurant())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for order with id: " + orderDto.getId()));
        Restaurant myRestaurant = restaurantPartner.getRestaurant();
        if(!myRestaurant.equals(orderRestaurant)) {
            throw new ResourceNotFoundException("Order with id " + orderDto.getId() + " not associated with your restaurant");
        }

//        100 + 20 + 10 + 5 = 130
//        1000 + 200 + 100 + 50 = 1350

        if(order.getOrderStatus().equals(OrderStatus.PENDING)){
            order.setOrderStatus(OrderStatus.BEING_PREPARED);
            //TODO: send notification to driver
            order = orderService.save(order);
        }
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto rejectOrder(OrderDto order) {
        return null;
    }

    @Override
    public OrderDto readyOrder(OrderDto orderDto) {
        RestaurantPartner restaurantPartner = currentLoggedRestaurantPartner();
        OrderEntity order = orderService.findById(orderDto.getId());
        Restaurant orderRestaurant =  order.getOrderItems().stream()
                .filter(Objects::nonNull)
                .map(orderItem -> orderItem.getFoodItem().getRestaurant())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for order with id: " + orderDto.getId()));
        Restaurant myRestaurant = restaurantPartner.getRestaurant();
        if(!myRestaurant.equals(orderRestaurant)) {
            throw new ResourceNotFoundException("Order with id " + orderDto.getId() + " not associated with your restaurant");
        }
        if(order.getOrderStatus().equals(OrderStatus.BEING_PREPARED)){
            order.setOrderStatus(OrderStatus.READY);
            order = orderService.save(order);
        }
        return modelMapper.map(order, OrderDto.class);
    }


    @Override
    public RestaurantPartner currentLoggedRestaurantPartner(){
        UserEntity user = userService.currentLoggedUser();
        return restaurantPartnerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Partner not associated for user with id " + user.getId()));
    }

    @Override
    public FoodItemDto addFoodItem(FoodItemDto requestDto) {

        RestaurantPartner restaurantPartner = currentLoggedRestaurantPartner();
        FoodItem foodItem = modelMapper.map(requestDto, FoodItem.class);
        foodItem.setId(null);
        foodItem.setRating(0.0);
        foodItem.setRestaurant(restaurantPartner.getRestaurant());

        return modelMapper.map(foodItemService.save(foodItem), FoodItemDto.class);
    }

    @Override
    public FoodItemDto removeFoodItem(Long id) {
        RestaurantPartner current = currentLoggedRestaurantPartner();
        FoodItem foodItem = foodItemService.findById(id);
        if(!foodItem.getRestaurant().equals(current.getRestaurant())){
            throw new BadRequestException("Food item is not associated with your restaurant");
        }
        foodItemService.delete(foodItem.getId());
        return modelMapper.map(foodItem, FoodItemDto.class);
    }


}
