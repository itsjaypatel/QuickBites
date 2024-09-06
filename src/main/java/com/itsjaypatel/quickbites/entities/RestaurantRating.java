package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.entities.compositekeys.RestaurantRatingPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(RestaurantRatingPK.class)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRating {

    @Id
    @ManyToOne
    private UserEntity user;

    @Id
    @ManyToOne
    private Restaurant restaurant;

    private Integer rating;
}
