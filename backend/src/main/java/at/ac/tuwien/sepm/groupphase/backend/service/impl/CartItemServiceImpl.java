package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
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
    private final CartRepository cartRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
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
    public Set<CartItem> updateCartItem(Cart cart,CartItem item) {
        LOGGER.trace("updateCartItem({},{})", cart, item);
        Set<CartItem> newCartItems = cart.getItems();
        CartItem oldCartItem = this.cartRepository.findCartItemInCartUsingSessionId(cart.getSessionId(), item.getProductId()).orElseThrow(() -> new NotFoundException("Could not find cart item to delete"));
        newCartItems.remove(oldCartItem);
        oldCartItem.setQuantity(item.getQuantity());
        newCartItems.add(oldCartItem);
        cart.setItems(newCartItems);
        return newCartItems;

    }


    @Transactional
    @Override
    public void deleteCartItemById(Cart cart, Long id) {
        LOGGER.trace("deleteCartItemById({},{})", cart, id);
        CartItem toDelete = this.cartRepository.findCartItemInCartUsingSessionId(cart.getSessionId(), id).orElseThrow(() -> new NotFoundException("Could not find cart item to delete"));
        cart.getItems().remove(toDelete);
        this.cartItemRepository.deleteCartItemById(toDelete.getId());
    }


}
