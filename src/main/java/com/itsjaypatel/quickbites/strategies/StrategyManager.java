package com.itsjaypatel.quickbites.strategies;

import com.itsjaypatel.quickbites.enums.PaymentMethod;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.strategies.impl.CODPaymentProcessor;
import com.itsjaypatel.quickbites.strategies.impl.WalletPaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StrategyManager {

    private final WalletPaymentProcessor walletPaymentProcessor;
    private final CODPaymentProcessor codPaymentProcessor;

    public PaymentProcessor getPaymentProcessor(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentProcessor;
            case CASH_ON_DELIVERY -> codPaymentProcessor;
            default -> throw new BadRequestException("Unsupported payment method");
        };
    }
}
