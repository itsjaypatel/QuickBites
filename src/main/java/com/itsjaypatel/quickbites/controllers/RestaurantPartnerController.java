package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.services.RestaurantPartnerService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/restaurant-partner")
@RequiredArgsConstructor
public class RestaurantPartnerController {

    private final RestaurantPartnerService restaurantPartnerService;

    @GetMapping("/profile")
    public ResponseEntity<?> viewProfile(){
        RestaurantPartnerDto restaurantPartner = restaurantPartnerService.getMyProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, restaurantPartner), HttpStatus.OK);
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = restaurantPartnerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK,orders), HttpStatus.OK);
    }

    @PatchMapping("/acceptOrder")
    public ResponseEntity<?> acceptOrder(@RequestBody OrderDto inputDto){
        OrderDto orderDto = restaurantPartnerService.acceptOrder(inputDto);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/rejectOrder")
    public ResponseEntity<?> rejectOrder(@RequestBody OrderDto inputDto){
        OrderDto orderDto = restaurantPartnerService.rejectOrder(inputDto);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/readyOrder")
    public ResponseEntity<?> readyOrder(@RequestBody OrderDto inputDto){
        OrderDto orderDto = restaurantPartnerService.readyOrder(inputDto);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/addFoodItem")
    public ResponseEntity<?> addFoodItem(@RequestBody FoodItemDto requestDto){
        FoodItemDto foodItemDto = restaurantPartnerService.addFoodItem(requestDto);
        return ResponseEntity.ok(foodItemDto);
    }

    @DeleteMapping("/deleteFoodItem/{foodItemId}")
    public ResponseEntity<?> deleteFoodItem(@PathVariable Long foodItemId){
        FoodItemDto foodItemDto = restaurantPartnerService.removeFoodItem(foodItemId);
        return ResponseEntity.ok(foodItemDto);
    }
}
