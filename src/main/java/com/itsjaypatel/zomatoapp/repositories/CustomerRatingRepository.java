package com.itsjaypatel.zomatoapp.repositories;

import com.itsjaypatel.zomatoapp.entities.CustomerRating;
import com.itsjaypatel.zomatoapp.entities.compositekeys.CustomerRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRatingRepository extends JpaRepository<CustomerRating, CustomerRatingPK> {

    @Query("SELECT avg(t.rating) from CustomerRating t where t.customer.id = :customerUserId")
    Double averageRatingForCustomer(Long customerUserId);
}
