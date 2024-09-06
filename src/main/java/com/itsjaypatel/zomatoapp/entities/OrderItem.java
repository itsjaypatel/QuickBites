package com.itsjaypatel.zomatoapp.entities;


import com.itsjaypatel.zomatoapp.entities.compositekeys.OrderItemPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(OrderItemPK.class)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Id
    @ManyToOne
    private FoodItem foodItem;

    private Integer quantity;
}
