package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPartnerDto {

    private Long id;

    private String registrationNo;

    private UserEntityDto user;

    private RestaurantDto restaurant;
}
