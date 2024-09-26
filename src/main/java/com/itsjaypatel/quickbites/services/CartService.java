package com.itsjaypatel.quickbites.services;

import com.itsjaypatel.quickbites.entities.Cart;
import com.itsjaypatel.quickbites.entities.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> checkout();

    Cart addToCart(Long foodId);

    Cart removeFromCart(Long foodId);
}
