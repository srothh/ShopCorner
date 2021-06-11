package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    /**
     * Deletes cartItems by id.
     *
     * @param id of the cartItem to be deleted
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCartItemById(Long id);
}
