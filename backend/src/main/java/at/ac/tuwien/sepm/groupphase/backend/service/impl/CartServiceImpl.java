package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
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


    @CacheEvict(value = "cartCounts", key = "#sessionId")
    @Override
    public Cart addCartItemToCart(UUID sessionId, CartItem item) {
        Cart cart = this.findCartBySessionId(sessionId);
        System.out.println(this.countCartItemInCartUsingSessionId(sessionId));
        if (this.countCartItemInCartUsingSessionId(sessionId) <= 20) {
            if (item.getQuantity() > 12) {
                item.setQuantity(12);
                throw new ServiceException("reached maximum of cart item");
            }
            cart.getItems().add(item);
        } else {
            throw new ServiceException("reached maximum of items in cart");
        }
        return this.addItemToCart(cart);
    }

    @CacheEvict(value = "cartCounts", key = "#sessionId")
    @Override
    public Cart addCartItemToNewCart(UUID sessionId, CartItem item) {
        Cart cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        if (item.getQuantity() > 12) {
            item.setQuantity(12);
        }
        cart.getItems().add(item);
        cart.setSessionId(sessionId);
        return this.createCart(cart);
    }

    @Cacheable(value = "cartCounts", key = "#sessionId")
    @Override
    public long countCartItemInCartUsingSessionId(UUID sessionId) {
        return this.cartRepository.countCartItemInCartUsingSessionId(sessionId).orElseThrow(() -> new NotFoundException("Could not find cart!"));
    }

    @Override
    public Cart createEmptyCart(UUID sessionId) {
        LOGGER.trace("createCart()");
        Cart cart = new Cart(sessionId, LocalDateTime.now());
        return this.createCart(cart);
    }

    @CacheEvict(value = "cartCounts", allEntries = true)
    @Override
    public Cart updateCart(Cart cart, CartItem item) {
        if (item.getQuantity() > 12) {
            item.setQuantity(12);
        }
        cart.setItems(this.cartItemService.updateCartItem(cart, item));
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart findCartBySessionId(UUID sessionId) {
        LOGGER.trace("findCartBySessionId({})", sessionId.toString());
        return this.cartRepository.findBySessionId(sessionId).orElseThrow(() -> new NotFoundException("Could not find cart!"));

    }

    @Override
    public boolean sessionIdExists(UUID sessionId) {
        LOGGER.trace("sessionIdExists({})", sessionId.toString());
        return sessionId.toString().matches("([A-Za-z0-9_-]*){36}$") && this.cartRepository.existsCartBySessionId(sessionId);
    }

    @Override
    public boolean validateSession(UUID sessionId) {
        LOGGER.trace("sessionIdExists({})", sessionId.toString());
        return sessionId.toString().matches("([A-Za-z0-9_-]*){36}$") && this.cartRepository.existsCartBySessionId(sessionId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0/5 * * ?")
    @CacheEvict(value = "cartCounts", allEntries = true)
    @Override
    public void deleteCartAfterDuration() {
        LOGGER.trace("deleteCartAfterDuration()");
        LocalDateTime timeBefore = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        this.cartRepository.deleteCartByCreatedAtIsBefore(timeBefore);
    }

    @CacheEvict(value = "cartCounts", allEntries = true)
    @Override
    public void deleteCartItemById(Long id) {
        this.cartItemService.deleteCartItemById(id);
    }


    private Cart addItemToCart(Cart cart) {
        LOGGER.trace("addItemToCart({})", cart);
        Set<CartItem> itemSet = cart.getItems();
        cart.setItems(this.cartItemService.createCartItems(itemSet));
        return this.cartRepository.save(cart);
    }

    private Cart createCart(Cart cart) {
        LOGGER.trace("createCart({})", cart);
        cart.setCreatedAt(LocalDateTime.now());
        Cart createdCart = this.cartRepository.save(cart);
        this.addItemToCart(createdCart);
        return this.cartRepository.save(cart);
    }

}
