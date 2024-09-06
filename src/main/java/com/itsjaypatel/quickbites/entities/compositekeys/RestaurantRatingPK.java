package com.itsjaypatel.quickbites.entities.compositekeys;


import com.itsjaypatel.quickbites.entities.Restaurant;
import com.itsjaypatel.quickbites.entities.UserEntity;
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
