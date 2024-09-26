package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.dtos.FoodItemDto;
import com.itsjaypatel.quickbites.entities.Favourites;
import com.itsjaypatel.quickbites.entities.FoodItem;
import com.itsjaypatel.quickbites.entities.FoodItemRating;
import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.FavouriteFoodItemRepository;
import com.itsjaypatel.quickbites.repositories.FoodItemRatingRepository;
import com.itsjaypatel.quickbites.repositories.FoodItemRepository;
import com.itsjaypatel.quickbites.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final FavouriteFoodItemRepository favouriteFoodItemRepository;
    private final FoodItemRatingRepository foodItemRatingRepository;
    private final IContextHolder contextHolder;

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
    public void addToFavorites(Long id) {
        FoodItem foodItem = findById(id);
        favouriteFoodItemRepository.save(new Favourites(contextHolder.customer(), foodItem));
    }

    @Override
    public void removeFromFavorites(Long id) {
        FoodItem foodItem = findById(id);
        favouriteFoodItemRepository.delete(new Favourites(contextHolder.customer(), foodItem));
    }

    @Override
    public void rateFood(Long foodId, Integer rating) {
        UserEntity user = contextHolder.user();
        FoodItem foodItem = findById(foodId);
        foodItemRatingRepository.save(new FoodItemRating(user, foodItem, rating));
        Double updatedAverageRating = foodItemRatingRepository.averageRatingForFoodItem(foodItem.getId());
        foodItem.setRating(updatedAverageRating);
        foodItemRepository.save(foodItem);
    }
}
