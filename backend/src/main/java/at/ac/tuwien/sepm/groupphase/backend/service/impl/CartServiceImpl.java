package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }



    @Override
    public Cart createCart(Cart cart) {
        LOGGER.trace("createCart({})", cart);
        cart.setCreatedAt(LocalDateTime.now());
        Set<CartItem> itemSet = cart.getItems();
        cart.setItems(null);
        Cart createdCart = this.cartRepository.save(cart);
        for (CartItem item : itemSet) {
            item.setCart(createdCart);
        }
        createdCart.setItems(this.cartItemService.createCartItem(itemSet));
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart addItemToCart(Cart cart) {
        LOGGER.trace("addItemToCart({})", cart);
        Set<CartItem> itemSet = cart.getItems();
        cart.setItems(null);
        for (CartItem item : itemSet) {
            item.setCart(cart);
        }
        cart.setItems(this.cartItemService.createCartItem(itemSet));
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart findCartBySessionId(UUID sessionId) {
        LOGGER.trace("findCartBySessionId({})", sessionId.toString());
        try {
            return this.cartRepository.findBySessionId(sessionId);
        } catch (RuntimeException e) {
            throw new NotFoundException("Could not find cart with the given sessionId %s");
        }
    }


    @Override
    public boolean sessionIdExists(UUID sessionId) {
        LOGGER.trace("sessionIdExists({})", sessionId.toString());
        return this.cartRepository.existsCartBySessionId(sessionId);
    }

    @Transactional
    @Scheduled(cron = "* */3 * * * *")
    @Override
    public void deleteCartAfterDuration() {
        LOGGER.trace("deleteCartAfterDuration()");
        LocalDateTime timeBefore = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        this.cartRepository.deleteCartByCreatedAtIsBefore(timeBefore);
    }
}
