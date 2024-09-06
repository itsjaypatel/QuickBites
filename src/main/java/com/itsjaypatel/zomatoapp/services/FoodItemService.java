package com.itsjaypatel.zomatoapp.services;

import com.itsjaypatel.zomatoapp.dtos.FoodItemDto;
import com.itsjaypatel.zomatoapp.entities.FoodItem;

import java.util.List;

public interface FoodItemService {

    FoodItem save(FoodItem foodItem);

    FoodItem update(FoodItemDto foodItemDto, Long id);

    void delete(Long id);

    FoodItem findById(Long id);

    List<FoodItem> search(String keyword);
}
