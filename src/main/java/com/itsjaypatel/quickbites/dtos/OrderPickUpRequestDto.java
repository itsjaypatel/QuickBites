package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPickUpRequestDto {
    private String orderId;
    private String otp;
}
