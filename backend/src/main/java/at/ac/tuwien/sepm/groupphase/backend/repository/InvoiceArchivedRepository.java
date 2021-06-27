package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceArchivedRepository extends JpaRepository<InvoiceArchive, Long> {

    /**
     * Checks whether the invoice exists.
     *
     * @param invoiceNumber assigned to the invoice
     * @return if exists true otherwise false
     * @throws RuntimeException upon encountering errors with the database
     */
    boolean existsInvoiceArchiveByInvoiceNumber(String invoiceNumber);

    /**
     * Find all a invoice by invoiceNumber.
     *
     * @param invoiceNumber assigned to the invoice
     * @return the invoice assigned to the session
     *
     * @throws RuntimeException upon encountering errors with the database
     */
    Optional<InvoiceArchive>  findInvoiceArchiveByInvoiceNumber(String invoiceNumber);

}
