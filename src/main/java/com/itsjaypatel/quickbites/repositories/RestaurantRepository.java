package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


}
