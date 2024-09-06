package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.entities.compositekeys.CartItemPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(CartItemPK.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {

    @Id
    @ManyToOne
    private Customer customer;

    @Id
    @ManyToOne
    private FoodItem foodItem;


    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
