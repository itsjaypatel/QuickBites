package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.dtos.OnboardDeliveryPartnerRequest;
import com.itsjaypatel.zomatoapp.dtos.OnboardRestaurantPartnerRequest;
import com.itsjaypatel.zomatoapp.entities.DeliveryPartner;
import com.itsjaypatel.zomatoapp.entities.Restaurant;
import com.itsjaypatel.zomatoapp.entities.RestaurantPartner;
import com.itsjaypatel.zomatoapp.entities.UserEntity;
import com.itsjaypatel.zomatoapp.enums.Role;
import com.itsjaypatel.zomatoapp.exceptions.ResourceConflictException;
import com.itsjaypatel.zomatoapp.exceptions.ResourceNotFoundException;
import com.itsjaypatel.zomatoapp.repositories.DeliveryPartnerRepository;
import com.itsjaypatel.zomatoapp.repositories.RestaurantPartnerRepository;
import com.itsjaypatel.zomatoapp.repositories.RestaurantRepository;
import com.itsjaypatel.zomatoapp.repositories.UserRepository;
import com.itsjaypatel.zomatoapp.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.itsjaypatel.zomatoapp.enums.Role.DELIVERY_PARTNER;
import static com.itsjaypatel.zomatoapp.enums.Role.RESTAURANT_PARTNER;

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
            throw new ResourceConflictException("User with id "+ request.getUserId() + " already restaurant partner");
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
