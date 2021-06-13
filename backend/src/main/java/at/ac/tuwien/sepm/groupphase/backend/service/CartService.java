package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public interface CartService {


    /**
     * Creates a new empty cart with a sessionId assigned to the client session.
     *
     * @param sessionId the id of the client session
     * @return The cart entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart createEmptyCart(UUID sessionId);


    /**
     * Adds a new cartItem to a new created cart with a sessionId assigned to the client session.
     *
     * @param sessionId the id of the client session
     * @param item The cart entity to with the new cartItem
     * @return The new cart entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart addCartItemToNewCart(UUID sessionId, CartItem item);

    /**
     * Adds a new cartItem to the cart with a sessionId assigned to the client session.
     *
     * @param sessionId the id of the client session
     * @param item The cart entity to with the new cartItem
     * @return The new cart entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart addCartItemToCart(UUID sessionId, CartItem item);


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
     * Updates a cartItem of a cart which is assigned to the client session.
     *
     * @param cart The cart entity which is assigned to the session
     * @param item The cartItem to be updated
     * @return The updated cart entity
     * @throws RuntimeException upon encountering errors with the database
     */
    Cart updateCart(Cart cart, CartItem item);


    /**
     * Find a single cart entry which is assigned to the client session.
     *
     * @param sessionId the sessionId of the session
     * @return true if sessionId is already in the database
     * @throws RuntimeException when no cart with the sessionId is found
     */
    boolean sessionIdExists(UUID sessionId);

    /**
     * Validates a single cart entry which is assigned to the client session.
     *
     * @param sessionId the sessionId of the session
     * @return true if session is valid
     * @throws RuntimeException when no cart with the sessionId is found
     */
    boolean validateSession(UUID sessionId);


    /**
     * Deletes a single cartItem in a cart which is assigned to the client session.
     *
     * @param sessionId the sessionId of the session
     * @param productId id of the product to be delete
     * @throws RuntimeException when no cart with the sessionId is found
     */
    void deleteCartItemInCart(UUID sessionId, Long productId);

    /**
     * Deletes carts after a certain time.
     * Will be every  5 hours.
     */
    @Scheduled(cron = "0 0 0/5 * * ?")
    void deleteCartAfterDuration();

}
