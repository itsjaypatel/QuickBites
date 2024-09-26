package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.dtos.*;
import com.itsjaypatel.quickbites.entities.*;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.DeliveryPartnerRatingRepository;
import com.itsjaypatel.quickbites.repositories.DeliveryPartnerRepository;
import com.itsjaypatel.quickbites.repositories.RestaurantRatingRepository;
import com.itsjaypatel.quickbites.repositories.RestaurantRepository;
import com.itsjaypatel.quickbites.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final DeliveryPartnerRatingRepository deliveryPartnerRatingRepository;
    private final RestaurantRatingRepository restaurantRatingRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderService orderService;
    private final WalletService walletService;
    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final IContextHolder contextHolder;
    private final FoodItemService foodItemService;


    @Override
    public CustomerDto getProfile() {
        return modelMapper.map(contextHolder.customer(), CustomerDto.class);
    }

    @Override
    public WalletDto getWallet() {
        UserEntity user = contextHolder.user();
        Wallet userWallet = walletService.getWalletByUser(user);
        List<WalletTransactionDto> transactions = walletService
                .getWalletTransactions(userWallet.getId())
                .stream().map(txn -> modelMapper.map(txn, WalletTransactionDto.class)).toList();
        WalletDto walletDto = modelMapper.map(userWallet, WalletDto.class);
        walletDto.setTransactions(transactions);
        return walletDto;
    }

    @Override
    public List<OrderDto> viewOrders() {
        Customer currentLoggedCustomer = contextHolder.customer();
        List<OrderDto> orders = orderService
                .findByCustomer(currentLoggedCustomer)
                .stream()
                .map(o -> modelMapper.map(o, OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public void rateDeliveryPartner(Long deliveryPartnerId, Integer rating) {
        UserEntity me = contextHolder.user();
        DeliveryPartner deliveryPartner = this
                .deliveryPartnerRepository
                .findById(deliveryPartnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not found with id " + deliveryPartnerId));
        UserEntity deliveryPartnerUser = deliveryPartner.getUser();
        deliveryPartnerRatingRepository
                .save(new DeliveryPartnerRating(me, deliveryPartnerUser, rating));

        Double updatedAverageRating = deliveryPartnerRatingRepository.averageRatingForDeliveryPartner(deliveryPartnerUser.getId());
        deliveryPartner.setRating(updatedAverageRating);

        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public void rateFood(Long foodItemId, Integer rating) {
        foodItemService.rateFood(foodItemId, rating);
    }

    @Override
    public void rateRestaurant(Long restaurantId, Integer rating) {
        UserEntity user = contextHolder.user();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurantRatingRepository.save(new RestaurantRating(user, restaurant, rating));
        Double updatedAverageRating = restaurantRatingRepository.averageRatingForRestaurant(restaurant.getId());

        restaurant.setRating(updatedAverageRating);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void addToFavourites(Long foodItemId) {
        foodItemService.addToFavorites(foodItemId);
    }

    @Override
    public void removeFromFavourites(Long foodItemId) {
        foodItemService.removeFromFavorites(foodItemId);
    }

    @Override
    public CartDto addToCart(Long foodId) {
        Cart cart = cartService.addToCart(foodId);
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto removeFromCart(Long foodId) {
        Cart cart = cartService.removeFromCart(foodId);
        return modelMapper.map(cart, CartDto.class);
    }
}
