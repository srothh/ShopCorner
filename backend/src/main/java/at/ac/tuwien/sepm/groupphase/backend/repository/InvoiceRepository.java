package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {


    /**
     * Find all invoice entries.
     *
     * @return list of al invoice entries
     * @throws RuntimeException upon encountering errors with the database
     */
    List<Invoice> findAll();

    /**
     * Returns the number of Invoices from one given year.
     *
     * @return number of invoices
     * @throws RuntimeException upon encountering errors with the database
     */
    Long countInvoiceByDateAfter(LocalDateTime date);

    /**
     * Finds a single invoice entry with the given id and customer id.
     *
     * @return single invoice entry with the given parameters
     * @throws RuntimeException upon encountering errors with the database
     */
    Optional<Invoice> findByIdAndCustomerId(Long id, Long categoryId);


    /**
     * Finds a single invoice entry with the given id and customer id.
     *
     * @return single invoice entry with the given parameters
     * @throws RuntimeException upon encountering errors with the database
     */
    Optional<Invoice> findByOrderNumber(String orderNumber);


}
