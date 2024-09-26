package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.UserEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

    Optional<DeliveryPartner> findByDrivingLicence(String id);

    Optional<DeliveryPartner> findByUser(UserEntity user);

    @Query(value = "SELECT d.*, ST_Distance(d.location, :pickupLocation) AS distance " +
            "FROM delivery_partner AS d " +
            "WHERE d.is_available = TRUE AND ST_DWithin(d.location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<DeliveryPartner> findNearestAroundRestaurant(Point pickupLocation);
}
