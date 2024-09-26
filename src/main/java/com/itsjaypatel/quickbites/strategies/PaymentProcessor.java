package com.itsjaypatel.quickbites.strategies;

import com.itsjaypatel.quickbites.dtos.PaymentDto;

public interface PaymentProcessor {
    PaymentDto process(String orderId);
}
