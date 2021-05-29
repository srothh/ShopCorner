package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {

    /**
     * Find a single message entry by id.
     *
     * @param id the id of the message entry
     * @return the message entry
     */
    Invoice findOneById(Long id);

    /**
     * Find a single message entry by id.
     *
     * @param date the id of the message entry
     * @return the message entry
     */
    // Invoice findOneByDate(LocalDateTime date);

    /**
     * Find all invoice entries.
     *
     * @return ordered list of al message entries
     */
    List<Invoice> findAllInvoices();

    /**
     * Create new invoice.
     *
     * @param invoice is the new invoice to create
     * @return new invoice
     */
    Invoice creatInvoice(Invoice invoice);



}
