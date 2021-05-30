package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;

public interface InvoiceService {

    /**
     * Find a single message entry by id.
     *
     * @param id the id of the message entry
     * @return the message entry
     * @throws NotFoundException when no invoice with the id is found
     */
    Invoice findOneById(Long id);


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
     * @throws ServiceException upon encountering errors with the database
     */
    Invoice createInvoice(Invoice invoice);



}
