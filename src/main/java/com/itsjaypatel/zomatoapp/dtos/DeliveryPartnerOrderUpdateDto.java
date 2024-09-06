package com.itsjaypatel.zomatoapp.dtos;

import com.itsjaypatel.zomatoapp.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerOrderUpdateDto {

    private Long orderId;

    private OrderStatus orderStatus;

    private String customerOtp;

    private String restaurantPartnerOtp;
}
