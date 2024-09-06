package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.entities.FoodItem;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.FoodItemRepository;
import com.itsjaypatel.quickbites.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;


    @Override
    public FoodItem save(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem update(FoodItemDto foodItemDto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        foodItemRepository.deleteById(id);
    }

    @Override
    public FoodItem findById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("FoodItem not found with id:" + id));
    }

    @Override
    public List<FoodItem> search(String keyword) {
        return List.of();
    }
}
