package com.itsjaypatel.quickbites.entities.compositekeys;

import com.itsjaypatel.quickbites.entities.FoodItem;
import com.itsjaypatel.quickbites.entities.OrderEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemPK {

    private OrderEntity order;

    private FoodItem foodItem;
}
