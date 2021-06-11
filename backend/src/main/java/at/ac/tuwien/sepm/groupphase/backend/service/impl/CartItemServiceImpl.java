package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
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
    public Set<CartItem> createCartItem(Set<CartItem> cartItem) {
        Set<CartItem> newCartItems = new HashSet<>();
        for (CartItem item : cartItem) {
            if (item != null || item.getId() != null) {
                newCartItems.add(this.cartItemRepository.save(item));
            }
        }
        return newCartItems;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return this.cartItemRepository.save(cartItem);
    }

    @Transactional
    @Override
    public CartItem updateCartItem(Cart cart, CartItem item) {
        CartItem toDelete = new CartItem();
        for (CartItem c : cart.getItems()) {
            System.out.println(c.getProductId().equals(item.getProductId()));
            if (c.getProductId().equals(item.getProductId())) {
                toDelete = c;
                break;
            }
        }
        this.deleteCartItem(toDelete);
        item.setCart(cart);
        return this.cartItemRepository.save(item);

    }

    @Transactional
    @Override
    public void deleteCartItem(CartItem cartItem) {
        this.cartItemRepository.deleteCartItemByCartItems(cartItem);
    }

}
