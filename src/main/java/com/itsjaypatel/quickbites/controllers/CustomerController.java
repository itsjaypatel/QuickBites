package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.*;
import com.itsjaypatel.quickbites.services.CatalogService;
import com.itsjaypatel.quickbites.services.CustomerService;
import com.itsjaypatel.quickbites.services.PaymentService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final CatalogService catalogService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> viewProfile() {
        log.info("Calling viewProfile");
        CustomerDto customer = customerService.getProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, customer), HttpStatus.OK);
    }

    @GetMapping("/viewWallet")
    public ResponseEntity<ApiResponse<?>> viewWallet() {
        log.info("Calling viewWallet");
        WalletDto response = customerService.getWallet();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, response), HttpStatus.OK);
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = customerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, orders), HttpStatus.OK);
    }

    @PostMapping("/rate/deliveryPartner/{deliveryPartnerId}/{rating}")
    public ResponseEntity<?> rateDeliveryPartner(
            @PathVariable
            Long deliveryPartnerId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating) {
        customerService.rateDeliveryPartner(deliveryPartnerId, rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/rate/food/{foodItemId}/{rating}")
    public ResponseEntity<?> rateFood(
            @PathVariable
            Long foodItemId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating) {
        customerService.rateFood(foodItemId, rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/rate/restaurant/{restaurantId}/{rating}")
    public ResponseEntity<?> rateRestaurant(
            @PathVariable
            Long restaurantId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating) {
        customerService.rateRestaurant(restaurantId, rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/favourites/add/{foodItemId}")
    public ResponseEntity<?> addToFavourites(
            @PathVariable
            Long foodItemId) {
        customerService.addToFavourites(foodItemId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/favourites/remove/{foodItemId}")
    public ResponseEntity<?> removeFromFavourites(
            @PathVariable
            Long foodItemId) {
        customerService.removeFromFavourites(foodItemId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/cart/add/{foodItemId}")
    public ResponseEntity<?> addToCart(
            @PathVariable
            Long foodItemId) {
        CartDto cart = customerService.addToCart(foodItemId);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, cart), HttpStatus.OK);
    }

    @PostMapping("/cart/remove/{foodItemId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable
            Long foodItemId) {
        CartDto cart = customerService.removeFromCart(foodItemId);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, cart), HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {
        OrderDto orderEntity = paymentService.checkout();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.CREATED, orderEntity),
                HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(
            @RequestBody
            CustomerPaymentRequestDto requestDto) {
        PaymentDto payment = paymentService.makePayment(requestDto.getOrderId(), requestDto.getPaymentMethod());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.CREATED, payment),
                HttpStatus.CREATED);
    }

    @PostMapping("/searchFood")
    public ResponseEntity<?> searchFood(@RequestBody SearchRequestDto requestDto) {
        var response = catalogService.searchFood(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
