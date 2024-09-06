package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnboardDeliveryPartnerRequest {

    private Long userId;

    private String licence;

    private PointDto location;
}
