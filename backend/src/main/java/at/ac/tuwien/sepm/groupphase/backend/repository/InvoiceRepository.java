package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    /**
     * Find all message entries ordered by published at date (descending).
     *
     * @return ordered list of al message entries
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
}
