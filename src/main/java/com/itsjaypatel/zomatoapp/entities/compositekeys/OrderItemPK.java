package com.itsjaypatel.zomatoapp.entities.compositekeys;

import com.itsjaypatel.zomatoapp.entities.FoodItem;
import com.itsjaypatel.zomatoapp.entities.OrderEntity;
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
