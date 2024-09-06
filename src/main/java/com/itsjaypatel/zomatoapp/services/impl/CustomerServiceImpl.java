package com.itsjaypatel.zomatoapp.services.impl;

import com.itsjaypatel.zomatoapp.dtos.CustomerDto;
import com.itsjaypatel.zomatoapp.dtos.OrderDto;
import com.itsjaypatel.zomatoapp.entities.*;
import com.itsjaypatel.zomatoapp.exceptions.ResourceNotFoundException;
import com.itsjaypatel.zomatoapp.repositories.*;
import com.itsjaypatel.zomatoapp.services.CustomerService;
import com.itsjaypatel.zomatoapp.services.OrderService;
import com.itsjaypatel.zomatoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;
    private final FavouriteFoodItemRepository favouriteFoodItemRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final DeliveryPartnerRatingRepository deliveryPartnerRatingRepository;
    private final FoodItemRatingRepository foodItemRatingRepository;
    private final RestaurantRatingRepository restaurantRatingRepository;
    private final RestaurantRepository restaurantRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;


    @Override
    public CustomerDto getProfile() {
        return modelMapper.map(this.currentLoggedCustomer(), CustomerDto.class);
    }

    @Override
    public List<OrderDto> viewOrders() {
        Customer currentLoggedCustomer = this.currentLoggedCustomer();
        List<OrderDto> orders = orderService
                .findByCustomer(currentLoggedCustomer)
                .stream()
                .map(o -> modelMapper.map(o,OrderDto.class))
                .toList();
        return orders;
    }

    @Override
    public void rateDeliveryPartner(Long deliveryPartnerId, Integer rating) {
        UserEntity me = userService.currentLoggedUser();
        DeliveryPartner deliveryPartner = this
                .deliveryPartnerRepository
                .findById(deliveryPartnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery partner not found with id " + deliveryPartnerId));
        UserEntity deliveryPartnerUser = deliveryPartner.getUser();
        deliveryPartnerRatingRepository
                .save(new DeliveryPartnerRating(me,deliveryPartnerUser,rating));

        Double updatedAverageRating = deliveryPartnerRatingRepository.averageRatingForDeliveryPartner(deliveryPartnerUser.getId());
        deliveryPartner.setRating(updatedAverageRating);

        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public void rateFood(Long foodItemId, Integer rating) {
        UserEntity user = userService.currentLoggedUser();
        FoodItem foodItem = foodItemRepository
                .findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodItemId));
        foodItemRatingRepository.save(new FoodItemRating(user,foodItem,rating));
        Double updatedAverageRating = foodItemRatingRepository.averageRatingForFoodItem(foodItem.getId());

        foodItem.setRating(updatedAverageRating);
        foodItemRepository.save(foodItem);
    }

    @Override
    public void rateRestaurant(Long restaurantId, Integer rating) {
        UserEntity user = userService.currentLoggedUser();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        restaurantRatingRepository.save(new RestaurantRating(user,restaurant,rating));
        Double updatedAverageRating = restaurantRatingRepository.averageRatingForRestaurant(restaurant.getId());

        restaurant.setRating(updatedAverageRating);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void addToFavourites(Long foodItemId) {
        Customer customer = this.currentLoggedCustomer();
        FoodItem foodItem = foodItemRepository
                .findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodItemId));
        favouriteFoodItemRepository.save(new Favourites(customer,foodItem));
    }

    @Override
    public void removeFromFavourites(Long foodItemId) {
        Customer customer = this.currentLoggedCustomer();
        FoodItem foodItem = foodItemRepository
                .findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodItemId));
        favouriteFoodItemRepository.delete(new Favourites(customer,foodItem));
    }

    @Override
    public void addToCart(Long foodItemId) {
        Customer customer = this.currentLoggedCustomer();
        FoodItem foodItem = foodItemRepository
                .findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodItemId));
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId())
        );
        CartItem cartItem = cartItemRepository
                .findByCustomerIdAndFoodItemId(customer.getId(),foodItem.getId())
                .orElse(
                        CartItem.builder()
                                .customer(customer)
                                .foodItem(foodItem)
                                .quantity(0).build());
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
    }

    @Override
    public void removeFromCart(Long foodItemId) {
        Customer customer = this.currentLoggedCustomer();
        FoodItem foodItem = foodItemRepository
                .findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodItemId));
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId())
        );
        CartItem cartItem = cartItemRepository
                .findByCustomerIdAndFoodItemId(customer.getId(),foodItem.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("FoodItem with id " + foodItem.getId() + " not found in cart for customer with id " + customer.getId()));
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        Set<CartItem> cartItems = cart.getCartItems();
        if(cartItem.getQuantity() == 0){
            cartItems.remove(cartItem);
        }else{
            cartItems.add(cartItem);
        }
        cart.setCartItems(cartItems);
    }

    @Override
    public Customer currentLoggedCustomer(){
        UserEntity user = userService.currentLoggedUser();
        return customerRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not associate with user with id " + user.getId()));
    }

//    public User currentLoggedUser(){
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
}
