package com.itsjaypatel.quickbites.context;

import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.RestaurantPartner;
import com.itsjaypatel.quickbites.entities.UserEntity;

public interface IContextHolder {

    UserEntity user();

    Customer customer();

    DeliveryPartner deliveryPartner();

    RestaurantPartner restaurantPartner();
}
