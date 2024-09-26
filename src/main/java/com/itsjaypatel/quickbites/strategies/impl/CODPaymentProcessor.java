package com.itsjaypatel.quickbites.strategies.impl;

import com.itsjaypatel.quickbites.dtos.PaymentDto;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.strategies.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CODPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentDto process(String orderId) {
        throw new BadRequestException("COD payment is not supported");
    }
}
