package com.itsjaypatel.quickbites.controllers;

import com.itsjaypatel.quickbites.dtos.DeliveryPartnerDto;
import com.itsjaypatel.quickbites.dtos.DeliveryPartnerOrderUpdateDto;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.services.DeliveryPartnerService;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import com.itsjaypatel.zomatoapp.dtos.*;
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
    public ResponseEntity<?> viewProfile(){
        DeliveryPartnerDto deliveryPartner = deliveryPartnerService.getMyProfile();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, deliveryPartner), HttpStatus.OK);
    }

    @GetMapping("/viewOrders")
    public ResponseEntity<?> viewOrders() {
        List<OrderDto> orders = deliveryPartnerService.viewOrders();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK,orders), HttpStatus.OK);
    }

    @PostMapping("/rateCustomer/{customerId}/{rating}")
    public ResponseEntity<?> rateCustomer(
            @PathVariable
            Long customerId,
            @PathVariable
            @Valid
            @Min(value = 1)
            @Max(value = 5)
            Integer rating){
        deliveryPartnerService.rateCustomer(customerId, rating);
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
        deliveryPartnerService.rateRestaurant(restaurantId, rating);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/acceptOrder")
    public ResponseEntity<?> acceptOrder(
            @RequestBody
            OrderDto inputOrderDto){
        OrderDto orderDto = deliveryPartnerService.acceptOrder(inputOrderDto);
        return ResponseEntity.ok().body(orderDto);
    }

    @PostMapping("/updateOrderStatus")
    public ResponseEntity<?> updateOrderStatus(
            @RequestBody
            DeliveryPartnerOrderUpdateDto inputDto){
        OrderDto orderDto =  deliveryPartnerService.updateOrderStatus(inputDto);
        return ResponseEntity.ok().body(orderDto);
    }
}
