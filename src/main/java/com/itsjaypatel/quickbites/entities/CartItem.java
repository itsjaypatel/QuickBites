package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.entities.compositekeys.CartItemPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
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
}
