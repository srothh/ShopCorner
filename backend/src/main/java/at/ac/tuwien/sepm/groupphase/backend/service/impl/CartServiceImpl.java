package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
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
        return this.cartRepository.findBySessionId(sessionId);
    }



    @Override
    public boolean sessionIdExists(UUID sessionId) {
        return this.cartRepository.existsCartBySessionId(sessionId);
    }

    //@Transactional
    //@Scheduled(cron = "* */3 * * * *")
    /*@Override
    public Long deleteCartAfterDuration() {
        LocalDateTime timeBefore = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        return this.cartRepository.deleteCartByCreatedAtIsBefore(timeBefore);
    }*/
}
