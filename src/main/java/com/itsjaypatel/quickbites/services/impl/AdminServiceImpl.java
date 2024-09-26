package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.dtos.OnboardDeliveryPartnerRequest;
import com.itsjaypatel.quickbites.dtos.OnboardRestaurantPartnerRequest;
import com.itsjaypatel.quickbites.entities.DeliveryPartner;
import com.itsjaypatel.quickbites.entities.Restaurant;
import com.itsjaypatel.quickbites.entities.RestaurantPartner;
import com.itsjaypatel.quickbites.entities.UserEntity;
import com.itsjaypatel.quickbites.enums.Role;
import com.itsjaypatel.quickbites.exceptions.ResourceConflictException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.DeliveryPartnerRepository;
import com.itsjaypatel.quickbites.repositories.RestaurantPartnerRepository;
import com.itsjaypatel.quickbites.repositories.RestaurantRepository;
import com.itsjaypatel.quickbites.repositories.UserRepository;
import com.itsjaypatel.quickbites.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.itsjaypatel.quickbites.enums.Role.DELIVERY_PARTNER;
import static com.itsjaypatel.quickbites.enums.Role.RESTAURANT_PARTNER;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public void onboardDeliveryPartner(OnboardDeliveryPartnerRequest request) {
        UserEntity user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()));
        Optional<DeliveryPartner> deliveryPartnerByUserIdOptional = deliveryPartnerRepository.findByUser(user);
        if (deliveryPartnerByUserIdOptional.isPresent()) {
            throw new ResourceConflictException("User with id " + request.getUserId() + " already delivery partner");
        }
        Optional<DeliveryPartner> deliveryPartnerOptional = deliveryPartnerRepository
                .findByDrivingLicence(request.getLicence());
        if (deliveryPartnerOptional.isPresent()) {
            throw new ResourceConflictException("Licence already associated with delivery partner");
        }

        Set<Role> updatedRoles = new HashSet<>(user.getRoles());
        updatedRoles.add(DELIVERY_PARTNER);
        user.setRoles(updatedRoles);
        userRepository.save(user);

        DeliveryPartner deliveryPartner = DeliveryPartner
                .builder()
                .user(user)
                .drivingLicence(request.getLicence())
                .location(modelMapper.map(request.getLocation(), Point.class))
                .rating(0.0)
                .build();
        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public void onboardRestaurantPartner(OnboardRestaurantPartnerRequest request) {
        UserEntity user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()));
        Optional<RestaurantPartner> restaurantPartnerByUserOptional = restaurantPartnerRepository.findByUser(user);
        if (restaurantPartnerByUserOptional.isPresent()) {
            throw new ResourceConflictException("User with id " + request.getUserId() + " already restaurant partner");
        }
        Optional<RestaurantPartner> restaurantPartnerByRegistrationNoOptional = restaurantPartnerRepository
                .findByRegistrationNo(request.getRegistrationNo());
        if (restaurantPartnerByRegistrationNoOptional.isPresent()) {
            throw new ResourceConflictException("Registration number already associated with restaurant partner");
        }

        Set<Role> updatedRoles = new HashSet<>(user.getRoles());
        updatedRoles.add(RESTAURANT_PARTNER);
        user.setRoles(updatedRoles);
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(Restaurant.builder()
                .name(request.getRestaurantName())
                .address(modelMapper.map(request.getRestaurantAddress(), Point.class))
                .build());

        RestaurantPartner restaurantPartner = restaurantPartnerRepository.save(RestaurantPartner
                .builder()
                .user(user)
                .restaurant(restaurant)
                .registrationNo(request.getRegistrationNo()).build());

    }

    @Override
    public void terminateDeliveryPartner(Long userId) {
        //TODO
    }

    @Override
    public void terminateRestaurantPartner(Long userId) {
        //TODO
    }
}
