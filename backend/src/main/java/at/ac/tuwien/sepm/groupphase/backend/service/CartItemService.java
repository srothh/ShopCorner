package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.Set;

public interface CartItemService {

    /**
     * Creates a new set of cartItems the individual elements are assigned to the session.
     *
     * @param cartItem The set to be created
     * @return The created cartItem set
     * @throws RuntimeException upon encountering errors with the database
     */
    Set<CartItem> createCartItem(Set<CartItem> cartItem);

    /**
     * Updates a new set of cartItems which is assigned to session.
     *
     * @param cart The cart with the sessionId
     * @param item The cartItem to be updated
     * @return the updated cartItem
     * @throws RuntimeException upon encountering errors with the database
     */
    CartItem updateCartItem(Cart cart, CartItem item);

    /**
     * Deletes a cartItem from the database.
     *
     * @param cartItem The cartItem to be deleted
     * @throws NotFoundException is thrown if the specified cartItem does not exists
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCartItem(CartItem cartItem);

    /**
     * Deletes a cartItem which is assigned to session by id.
     *
     * @param cart The cart with the sessionId
     * @param id The id of the cartItem to be deleted
     * @throws NotFoundException is thrown if the specified cartItem does not exists
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCartItemById(Cart cart, Long id);

}
