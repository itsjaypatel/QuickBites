package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnboardRestaurantPartnerRequest {

    private Long userId;

    private String registrationNo;

    private String restaurantName;

    private PointDto restaurantAddress;
}
