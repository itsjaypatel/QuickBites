package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.entities.FoodItem;

public interface FoodItemService {

    FoodItem save(FoodItem foodItem);

    FoodItem update(FoodItemDto foodItemDto, Long id);

    void delete(Long id);

    FoodItem findById(Long id);

    void addToFavorites(Long id);

    void removeFromFavorites(Long id);

    void rateFood(Long foodId, Integer rating);
}
