package com.itsjaypatel.quickbites.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    @JsonIgnore
    private CustomerDto customer;

    private FoodItemDto foodItem;

    private Integer quantity;

    @JsonIgnore
    private CartDto cart;
}
