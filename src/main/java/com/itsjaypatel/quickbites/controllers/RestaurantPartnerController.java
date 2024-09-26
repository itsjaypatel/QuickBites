package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.RestaurantPartnerDto;
import com.itsjaypatel.quickbites.services.RestaurantPartnerService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
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
    public ResponseEntity<?> viewProfile() {
        RestaurantPartnerDto restaurantPartner = restaurantPartnerService.getMyProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, restaurantPartner), HttpStatus.OK);
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = restaurantPartnerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, orders), HttpStatus.OK);
    }

    @PatchMapping("/order/accept/{orderId}")
    public ResponseEntity<?> acceptOrder(@PathVariable String orderId) {
        OrderDto orderDto = restaurantPartnerService.acceptOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/order/reject/{orderId}")
    public ResponseEntity<?> rejectOrder(@PathVariable String orderId) {
        OrderDto orderDto = restaurantPartnerService.rejectOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/food/add")
    public ResponseEntity<?> addFoodItem(@RequestBody FoodItemDto requestDto) {
        FoodItemDto foodItemDto = restaurantPartnerService.addFoodItem(requestDto);
        return ResponseEntity.ok(foodItemDto);
    }

    @DeleteMapping("/food/delete/{foodId}")
    public ResponseEntity<?> deleteFoodItem(@PathVariable Long foodId) {
        FoodItemDto foodItemDto = restaurantPartnerService.removeFoodItem(foodId);
        return ResponseEntity.ok(foodItemDto);
    }
}
