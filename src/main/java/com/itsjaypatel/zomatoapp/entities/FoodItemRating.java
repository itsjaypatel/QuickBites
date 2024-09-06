package com.itsjaypatel.zomatoapp.entities;

import com.itsjaypatel.zomatoapp.entities.compositekeys.FoodItemRatingPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(FoodItemRatingPK.class)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemRating {

    @Id
    @ManyToOne
    private UserEntity user;

    @Id
    @ManyToOne
    private FoodItem foodItem;


    private Integer rating;
}
