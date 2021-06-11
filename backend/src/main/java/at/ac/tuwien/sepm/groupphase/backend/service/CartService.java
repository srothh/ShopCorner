package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public interface CartService {


    /**
     * Creates a new cart with a sessionId assigned to the client session.
     *
     * @param cart The cart entity to save
     * @return The cart entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart createCart(Cart cart);

    /**
     * Adds a new cartItem to the cart with a sessionId assigned to the client session.
     *
     * @param cart The cart entity to with the new cartItem
     * @return The new cart entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart addItemToCart(Cart cart);

    /**
     * Find a single cart entry which is assigned to the client session.
     *
     * @param sessionId The sessionId assigned to the session
     * @return cart entity
     * @throws NotFoundException is thrown if the specified cart does not exists
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart findCartBySessionId(UUID sessionId);


    /**
     * Find a single cart entry which is assigned to the client session.
     *
     * @param sessionId the sessionId of the session
     * @return true if sessionId is already in the database
     * @throws RuntimeException when no cart with the sessionId is found
     */
    boolean sessionIdExists(UUID sessionId);

    /**
     * Deletes carts after a certain time.
     * Will be every  5 hours.
     */
    @Scheduled(cron = "0 0 0/5 * * ?")
    void deleteCartAfterDuration();

}
