package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find order by an given invoice entry.
     *
     * @param invoice that should be searched for
     * @return order with the specific invoice
     * @throws RuntimeException upon encountering errors with the database
     */
    Optional<Order> findOrderByInvoice(Invoice invoice);

    /**
     * Find a page of orders by a given customer id.
     *
     * @param page the page-able request to retrieve page-able objects
     * @param customerId the customerId to look after
     * @return order with the specific invoice
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Order> findAllByCustomerId(Pageable page, Long customerId);

}
