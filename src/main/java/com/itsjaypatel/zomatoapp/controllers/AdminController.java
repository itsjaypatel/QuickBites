package com.itsjaypatel.zomatoapp.controllers;

import com.itsjaypatel.zomatoapp.dtos.OnboardDeliveryPartnerRequest;
import com.itsjaypatel.zomatoapp.dtos.OnboardRestaurantPartnerRequest;
import com.itsjaypatel.zomatoapp.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/onboard/deliveryPartner")
    public ResponseEntity<?> onboardDeliveryPartner(@RequestBody OnboardDeliveryPartnerRequest request){
        adminService.onboardDeliveryPartner(request);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/onboard/restaurantPartner")
    public ResponseEntity<?> onboardRestaurantPartner(@RequestBody OnboardRestaurantPartnerRequest request){
        adminService.onboardRestaurantPartner(request);
        return ResponseEntity.ok().body("success");
    }
}