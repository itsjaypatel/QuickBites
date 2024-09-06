package com.itsjaypatel.quickbites.services;


import com.itsjaypatel.quickbites.enums.PaymentMethod;
import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.PaymentDto;

public interface PaymentService {

    OrderDto checkout();

    PaymentDto makePayment(Long orderId, PaymentMethod paymentMethod);

    PaymentDto updatePayment(PaymentDto paymentDto);

//    PaymentDto successfulPayment(Long paymentId);
//
//    PaymentDto failedPayment(Long paymentId);
}
