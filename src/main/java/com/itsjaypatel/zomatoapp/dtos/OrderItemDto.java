package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private FoodItemDto foodItem;

    private Integer quantity;
}