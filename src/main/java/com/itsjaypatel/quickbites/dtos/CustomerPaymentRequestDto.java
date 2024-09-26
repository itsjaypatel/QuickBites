package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentRequestDto {

    private String orderId;

    private PaymentMethod paymentMethod;
}
