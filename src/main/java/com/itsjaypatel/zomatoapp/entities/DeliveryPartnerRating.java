package com.itsjaypatel.zomatoapp.entities;

import com.itsjaypatel.zomatoapp.entities.compositekeys.DeliveryPartnerRatingPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(DeliveryPartnerRatingPK.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRating {

    @Id
    @ManyToOne
    private UserEntity customer;

    @Id
    @ManyToOne
    private UserEntity deliveryPartner;


    private Integer rating;
}
