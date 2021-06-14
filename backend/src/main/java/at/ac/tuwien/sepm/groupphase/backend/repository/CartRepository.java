package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    /**
     * Checks whether the session exists.
     *
     * @param sessionId assigned to the session
     * @return if exists true otherwise false
     * @throws RuntimeException upon encountering errors with the database
     */
    boolean existsCartBySessionId(UUID sessionId);

    /**
     * Deletes carts after a certain time.
     *
     * @param date everything before will be deleted
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCartByCreatedAtIsBefore(LocalDateTime date);

    /**
     * Find all a cart by sessionId.
     *
     * @param sessionId assigned to the session
     * @return the cart assigned to the session
     *
     * @throws RuntimeException upon encountering errors with the database
     */
    @Query(value = "select c from Cart c where c.sessionId = (:sessionId)")
    Optional<Cart> findBySessionId(@Param("sessionId") UUID sessionId);


    /**
     * Finds a cartItem of a cart by sessionId and productId.
     *
     * @param sessionId assigned to the session
     * @param productId assigned to the session
     * @return the cartItem of the cart assigned to the session
     * @throws RuntimeException upon encountering errors with the database
     */
    @Query(value = "select i from Cart c INNER JOIN c.items i where c.sessionId = (:sessionId) and i.productId=(:productId)")
    Optional<CartItem> findCartItemInCartUsingSessionId(@Param("sessionId") UUID sessionId, @Param("productId") Long productId);



    /**
     * Counts the Items of a cart by the sessionId assigned to the client session.
     *
     * @param sessionId assigned to the session
     * @return the cartItem of the cart assigned to the session
     * @throws RuntimeException upon encountering errors with the database
     */
    @Query(value = "select count(i) from Cart c INNER JOIN c.items i where c.sessionId = (:sessionId)")
    Optional<Integer> countCartItemInCartUsingSessionId(@Param("sessionId") UUID sessionId);
}
