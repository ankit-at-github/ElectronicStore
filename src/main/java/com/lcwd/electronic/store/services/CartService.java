package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {

    /*
    Add items to cart
    Case 1: Cart for user is not available, we will create a cart and add items
    Case 2: Car for user is available, add items to cart
     */
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    /*
    Remove Item from Cart
     */
    void removeItemFromCart(String userId, int cartItem);

    //Remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
