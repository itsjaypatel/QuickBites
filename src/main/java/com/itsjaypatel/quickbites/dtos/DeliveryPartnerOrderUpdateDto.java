package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerOrderUpdateDto {

    private String orderId;

    private OrderStatus orderStatus;

    private String customerOtp;

    private String restaurantPartnerOtp;
}
