package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerDto {

    private Long id;

    private UserEntityDto user;

    private Double rating;

    private String drivingLicence;

    private PointDto location;
}
