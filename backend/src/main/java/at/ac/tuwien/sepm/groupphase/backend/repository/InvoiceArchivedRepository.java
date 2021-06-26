package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceArchivedRepository extends JpaRepository<InvoiceArchive, Long> {

    boolean existsInvoiceArchiveByInvoiceNumber(String invoiceNumber);

    Optional<InvoiceArchive>  findInvoiceArchiveByInvoiceNumber(String invoiceNumber);

}
