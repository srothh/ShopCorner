package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addProductsToCart(Cart cart) {
        return this.cartRepository.save(cart);
    }

    @Override
    public boolean sessionIdExists(UUID sessionId) {
        return this.cartRepository.existsCartBySessionId(sessionId);
    }

    @Scheduled(cron = "* */3 * * * *")
    @Override
    public Long deleteCartAfterDuration() {
        LocalDateTime timeBefore = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        return this.cartRepository.deleteCartByCreatedAtIsBefore(timeBefore);
    }
}
