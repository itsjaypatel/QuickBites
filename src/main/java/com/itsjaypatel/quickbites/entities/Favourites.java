package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.entities.compositekeys.FavouritesPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;

@IdClass(FavouritesPK.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Favourites {

    @Id
    @ManyToOne
    private Customer customer;

    @Id
    @ManyToOne
    private FoodItem foodItem;
}



