package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

@Service
public class CartItemServiceImpl implements CartItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Set<CartItem> createCartItems(Set<CartItem> cartItem) {
        LOGGER.trace("createCartItem({})", cartItem);
        Set<CartItem> newCartItems = new HashSet<>();
        for (CartItem item : cartItem) {
            if (item != null) {
                newCartItems.add(this.cartItemRepository.save(item));
            }
        }
        return newCartItems;
    }


    @Transactional
    @Override
    public Set<CartItem> updateCartItem(Cart cart, CartItem cartItem) {
        LOGGER.trace("updateCartItem({},{})", cart, cartItem);
        Set<CartItem> cartItems = cart.getItems();
        CartItem oldCartItem = this.cartItemRepository.findById(cartItem.getId()).orElseThrow(() -> new NotFoundException("Could not find cart item to delete"));
        cartItems.remove(oldCartItem);
        oldCartItem.setQuantity(cartItem.getQuantity());
        cartItems.add(oldCartItem);
        cart.setItems(cartItems);
        return cartItems;
    }


    @Override
    public void deleteCartItemById(Long id) {
        LOGGER.trace("deleteCartItemById({})", id);
        this.cartItemRepository.deleteById(id);
    }

}
