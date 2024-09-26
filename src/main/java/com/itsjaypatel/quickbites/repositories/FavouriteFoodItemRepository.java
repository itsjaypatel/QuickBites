package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.Favourites;
import com.itsjaypatel.quickbites.entities.compositekeys.FavouritesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteFoodItemRepository extends JpaRepository<Favourites, FavouritesPK> {

    @Query("SELECT f from Favourites f where f.customer.id = :customerId and f.foodItem.id = :foodItemId")
    Optional<Favourites> findByCustomerIdAndFoodItemId(Long customerId, Long foodItemId);
}
