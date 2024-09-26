package com.itsjaypatel.quickbites.services.impl;

import com.itsjaypatel.quickbites.context.IContextHolder;
import com.itsjaypatel.quickbites.entities.Cart;
import com.itsjaypatel.quickbites.entities.CartItem;
import com.itsjaypatel.quickbites.entities.Customer;
import com.itsjaypatel.quickbites.entities.FoodItem;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.repositories.CartItemRepository;
import com.itsjaypatel.quickbites.repositories.CartRepository;
import com.itsjaypatel.quickbites.repositories.FoodItemRepository;
import com.itsjaypatel.quickbites.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final IContextHolder contextHolder;

    @Override
    public List<CartItem> checkout() {

        Customer customer = contextHolder.customer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId()));
        Set<CartItem> cartItemSet = cart.getCartItems();
        if (cartItemSet.isEmpty()) {
            throw new BadRequestException("Cart is empty for customer with id " + customer.getId());
        }

        cart.setCartItems(Set.of());
        cartItemRepository.deleteAll(cartItemSet);
        cartRepository.save(cart);

        return new ArrayList<>(cartItemSet);
    }

    @Override
    public Cart addToCart(Long foodId) {
        Customer customer = contextHolder.customer();
        FoodItem foodItem = foodItemRepository
                .findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodId));
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId())
        );
        CartItem cartItem = cartItemRepository
                .findByCustomerIdAndFoodItemId(customer.getId(), foodItem.getId())
                .orElse(
                        CartItem.builder()
                                .customer(customer)
                                .foodItem(foodItem)
                                .quantity(0).build());
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        Set<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> existingCartItem = cartItems.stream().findAny();
        if (existingCartItem.isPresent() && !existingCartItem.get().getFoodItem().getRestaurant().equals(foodItem.getRestaurant())) {
            throw new BadRequestException("FoodItem from more than one restaurant in single cart not allowed");
        }
        cartItems.add(cartItem);
        cartItemRepository.save(cartItem);
        cart.setCartItems(cartItems);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeFromCart(Long foodId) {
        Customer customer = contextHolder.customer();
        FoodItem foodItem = foodItemRepository
                .findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + foodId));
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not associate with customer with id " + customer.getId())
        );
        CartItem cartItem = cartItemRepository
                .findByCustomerIdAndFoodItemId(customer.getId(), foodItem.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("FoodItem with id " + foodItem.getId() + " not found in cart for customer with id " + customer.getId()));
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItem.getQuantity() == 0) {
            cartItems.remove(cartItem);
            cartItemRepository.delete(cartItem);
            cart.setCartItems(cartItems);
        } else {
            cartItems.add(cartItem);
            cartItemRepository.save(cartItem);
        }
        return cartRepository.save(cart);
    }
}
