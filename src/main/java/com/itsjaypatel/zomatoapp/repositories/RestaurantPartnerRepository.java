package com.itsjaypatel.zomatoapp.repositories;

import com.itsjaypatel.zomatoapp.entities.RestaurantPartner;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner, Long> {

    Optional<RestaurantPartner> findByUser(UserEntity user);

    Optional<RestaurantPartner> findByRegistrationNo(String registrationNo);

    Optional<RestaurantPartner> findByRestaurantId(Long restaurantId);
}
