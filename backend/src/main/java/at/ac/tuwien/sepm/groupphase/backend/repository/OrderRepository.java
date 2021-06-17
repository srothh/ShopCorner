package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find order by an given invoice entry.
     *
     * @return order with the specific invoice
     * @throws RuntimeException upon encountering errors with the database
     */
    Optional<Order> findOrderByInvoice(Invoice invoice);

}
