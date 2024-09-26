package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.DeliveryPartnerDto;
import com.itsjaypatel.quickbites.dtos.OrderDeliveredRequestDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.OrderPickUpRequestDto;
import com.itsjaypatel.quickbites.services.DeliveryPartnerService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/delivery-partner")
@RequiredArgsConstructor
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    @GetMapping("/profile")
    public ResponseEntity<?> viewProfile() {
        DeliveryPartnerDto deliveryPartner = deliveryPartnerService.getMyProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, deliveryPartner), HttpStatus.OK);
    }

    @PatchMapping("/availability")
    public ResponseEntity<?> setAvailability(@RequestParam Boolean isAvailable) {
        deliveryPartnerService.setAvailability(isAvailable);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = deliveryPartnerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, orders), HttpStatus.OK);
    }

    @PostMapping("/rate/customer/{customerId}/{rating}")
    public ResponseEntity<?> rateCustomer(
            @PathVariable
            Long customerId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating) {
        deliveryPartnerService.rateCustomer(customerId, rating);
        return ResponseEntity.ok().body("success");
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
        deliveryPartnerService.rateRestaurant(restaurantId, rating);
        return ResponseEntity.ok().body("success");
    }

    @PatchMapping("/order/accept/{orderId}")
    public ResponseEntity<?> acceptOrder(
            @PathVariable
            String orderId) {
        OrderDto orderDto = deliveryPartnerService.acceptOrder(orderId);
        return ResponseEntity.ok().body(orderDto);
    }

    @PatchMapping("/order/reject/{orderId}")
    public ResponseEntity<?> rejectOrder(
            @PathVariable
            String orderId) {
        OrderDto orderDto = deliveryPartnerService.rejectOrder(orderId);
        return ResponseEntity.ok().body(orderDto);
    }

    @PatchMapping("/order/pickup")
    public ResponseEntity<?> pickupOrder(
            @RequestBody
            OrderPickUpRequestDto request) {
        OrderDto orderDto = deliveryPartnerService.pickUpOrder(request);
        return ResponseEntity.ok().body(orderDto);
    }

    @PatchMapping("/order/deliver")
    public ResponseEntity<?> deliverOrder(
            @RequestBody
            OrderDeliveredRequestDto request) {
        OrderDto orderDto = deliveryPartnerService.deliverOrder(request);
        return ResponseEntity.ok().body(orderDto);
    }

}
