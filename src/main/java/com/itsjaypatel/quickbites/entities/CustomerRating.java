package com.itsjaypatel.quickbites.entities;

import com.itsjaypatel.quickbites.entities.compositekeys.CustomerRatingPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(CustomerRatingPK.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRating {

    @Id
    @ManyToOne
    private UserEntity deliveryPartner;

    @Id
    @ManyToOne
    private UserEntity customer;

    private Integer rating;
}
