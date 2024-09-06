package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.RestaurantRating;
import com.itsjaypatel.quickbites.entities.compositekeys.RestaurantRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, RestaurantRatingPK> {

    @Query("SELECT avg(t.rating) from RestaurantRating t where t.restaurant.id = :restaurantId")
    Double averageRatingForRestaurant(Long restaurantId);
}
