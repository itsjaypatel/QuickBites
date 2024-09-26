package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {


    private Long id;

    private CustomerDto customer;

    private Set<CartItemDto> cartItems;
}
