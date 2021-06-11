package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;

import java.util.Set;

public interface CartItemService {

    Set<CartItem> createCartItem(Set<CartItem> cartItem);

    CartItem updateCartItem(Cart cart, CartItem item);

    void deleteCartItem(CartItem cartItem);

    void deleteCartItemById(Cart cart,Long id);

}
