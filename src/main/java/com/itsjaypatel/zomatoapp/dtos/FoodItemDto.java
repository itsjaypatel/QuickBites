package com.itsjaypatel.zomatoapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDto {


    private Long id;

    private String title;

    private String imageUrl;

    private RestaurantDto restaurant;

    private Double price;

    private Double rating = 0.0;
}
