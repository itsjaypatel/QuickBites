package com.itsjaypatel.zomatoapp.services;


import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.dtos.PaymentDto;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;
import com.itsjaypatel.zomatoapp.enums.PaymentMethod;

public interface PaymentService {

    OrderDto checkout();

    PaymentDto makePayment(Long orderId, PaymentMethod paymentMethod);

    PaymentDto updatePayment(PaymentDto paymentDto);

//    PaymentDto successfulPayment(Long paymentId);
//
//    PaymentDto failedPayment(Long paymentId);
}
