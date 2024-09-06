package com.itsjaypatel.zomatoapp.entities.compositekeys;


import com.itsjaypatel.zomatoapp.entities.Restaurant;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantRatingPK implements Serializable {

    private UserEntity user;

    private Restaurant restaurant;
}
