package com.itsjaypatel.quickbites.entities.compositekeys;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.FoodItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class CartItemPK implements Serializable {

    private Customer customer;

    private FoodItem foodItem;
}
