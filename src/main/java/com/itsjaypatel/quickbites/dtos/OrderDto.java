package com.itsjaypatel.quickbites.dtos;

import com.itsjaypatel.quickbites.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;

    private Double amount;

    private Set<OrderItemDto> orderItems;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;
}
