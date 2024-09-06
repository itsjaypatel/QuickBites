package com.itsjaypatel.quickbites.entities.compositekeys;

import com.itsjaypatel.quickbites.entities.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class DeliveryPartnerRatingPK implements Serializable {

    private UserEntity customer;

    private UserEntity deliveryPartner;
}
