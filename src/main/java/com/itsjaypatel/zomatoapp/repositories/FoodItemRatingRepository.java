package com.itsjaypatel.zomatoapp.repositories;

import com.itsjaypatel.zomatoapp.entities.FoodItemRating;
import com.itsjaypatel.zomatoapp.entities.compositekeys.FoodItemRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRatingRepository extends JpaRepository<FoodItemRating, FoodItemRatingPK> {

    @Query("SELECT avg(t.rating) from FoodItemRating t where t.foodItem.id = :foodItemId")
    Double averageRatingForFoodItem(Long foodItemId);
}
