package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessRequestDto {

    private String paymentId;

    private String transactionId;
}
