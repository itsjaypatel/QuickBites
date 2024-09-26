package com.itsjaypatel.quickbites.context.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.RestaurantPartner;
import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.CustomerRepository;
import com.itsjaypatel.quickbites.repositories.DeliveryPartnerRepository;
import com.itsjaypatel.quickbites.repositories.RestaurantPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppContextHolder implements IContextHolder {

    private final CustomerRepository customerRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final RestaurantPartnerRepository restaurantPartnerRepository;

    @Override
    public UserEntity user() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Customer customer() {
        UserEntity user = user();
        return customerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not associate with user with id " + user.getId()));
    }

    @Override
    public DeliveryPartner deliveryPartner() {
        UserEntity user = user();
        return deliveryPartnerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not associated with user with id " + user.getId()));
    }

    @Override
    public RestaurantPartner restaurantPartner() {
        UserEntity user = user();
        return restaurantPartnerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Partner not associated for user with id " + user.getId()));
    }
}
