package com.itsjaypatel.quickbites.repositories;

import com.itsjaypatel.quickbites.entities.DeliveryPartnerRating;
import com.itsjaypatel.quickbites.entities.compositekeys.DeliveryPartnerRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRatingRepository extends JpaRepository<DeliveryPartnerRating, DeliveryPartnerRatingPK> {

    @Query("SELECT avg(t.rating) from DeliveryPartnerRating t where t.deliveryPartner.id = :deliveryPartnerUserId")
    Double averageRatingForDeliveryPartner(Long deliveryPartnerUserId);
}
