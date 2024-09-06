package com.itsjaypatel.zomatoapp.entities.compositekeys;


import com.itsjaypatel.zomatoapp.entities.FoodItem;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class FoodItemRatingPK implements Serializable {

    private UserEntity user;

    private FoodItem foodItem;
}
