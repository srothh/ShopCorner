package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    /**
     * Find all message entries ordered by published at date (descending).
     *
     * @return ordered list of al message entries
     * @throws RuntimeException upon encountering errors with the database
     */
    boolean existsCartBySessionId(UUID sessionId);

    Long deleteCartByCreatedAtIsBefore(LocalDateTime sessionId);
}
