package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String id;

    private OrderDto order;

    private Double amount;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime createdAt;
}
