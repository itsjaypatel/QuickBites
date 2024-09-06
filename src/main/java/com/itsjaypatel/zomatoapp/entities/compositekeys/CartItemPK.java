package com.itsjaypatel.zomatoapp.entities.compositekeys;

import com.itsjaypatel.zomatoapp.entities.Customer;
import com.itsjaypatel.zomatoapp.entities.FoodItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class CartItemPK implements Serializable {

    private Customer customer;

    private FoodItem foodItem;
}
