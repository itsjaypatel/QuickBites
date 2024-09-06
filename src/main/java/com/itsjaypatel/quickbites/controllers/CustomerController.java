package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.CustomerDto;
import com.itsjaypatel.quickbites.dtos.CustomerPaymentRequestDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.PaymentDto;
import com.itsjaypatel.quickbites.services.CustomerService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import com.itsjaypatel.zomatoapp.dtos.*;
import com.itsjaypatel.quickbites.services.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PaymentService paymentService;

    @GetMapping("/profile")
    public ResponseEntity<?> viewProfile(){
        CustomerDto customer = customerService.getProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, customer), HttpStatus.OK);
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = customerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK,orders), HttpStatus.OK);
    }


    @PostMapping("/rateDeliveryPartner/{deliveryPartnerId}/{rating}")
    public ResponseEntity<?> rateDeliveryPartner(
            @PathVariable
            Long deliveryPartnerId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating){
        customerService.rateDeliveryPartner(deliveryPartnerId, rating);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/rateFood/{foodItemId}/{rating}")
    public ResponseEntity<?> rateFood(
            @PathVariable
            Long foodItemId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating){
        customerService.rateFood(foodItemId, rating);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/rateRestaurant/{restaurantId}/{rating}")
    public ResponseEntity<?> rateRestaurant(
            @PathVariable
            Long restaurantId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating){
        customerService.rateRestaurant(restaurantId, rating);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/addToFavourites/{foodItemId}")
    public ResponseEntity<?> addToFavourites(
            @PathVariable
            Long foodItemId){
        customerService.addToFavourites(foodItemId);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/removeFromFavourites/{foodItemId}")
    public ResponseEntity<?> removeFromFavourites(
            @PathVariable
            Long foodItemId){
        customerService.removeFromFavourites(foodItemId);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/addToCart/{foodItemId}")
    public ResponseEntity<?> addToCart(
            @PathVariable
            Long foodItemId){
        customerService.addToCart(foodItemId);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/removeFromCart/{foodItemId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable
            Long foodItemId){
        customerService.removeFromCart(foodItemId);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(){
        OrderDto orderEntity = paymentService.checkout();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.CREATED,orderEntity),
                HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(
        @RequestBody
        CustomerPaymentRequestDto requestDto){
        PaymentDto payment = paymentService.makePayment(requestDto.getOrderId(), requestDto.getPaymentMethod());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.CREATED,payment),
                HttpStatus.CREATED);
    }
}
