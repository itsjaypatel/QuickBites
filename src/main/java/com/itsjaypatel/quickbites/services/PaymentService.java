package com.itsjaypatel.quickbites.services;


import com.itsjaypatel.quickbites.dtos.OrderDto;
import com.itsjaypatel.quickbites.dtos.PaymentDto;
import com.itsjaypatel.quickbites.enums.PaymentMethod;

public interface PaymentService {

    OrderDto checkout();

    PaymentDto makePayment(String orderId, PaymentMethod paymentMethod);

    PaymentDto updatePayment(PaymentDto paymentDto);

//    PaymentDto successfulPayment(Long paymentId);
//
//    PaymentDto failedPayment(Long paymentId);
}
