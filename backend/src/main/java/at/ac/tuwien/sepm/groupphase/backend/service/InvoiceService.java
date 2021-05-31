package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;

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
     * @return ordered list of all message entries
     */
    List<Invoice> findAllInvoices();

    /**
     * Retrieves a PaginationDto containing a Page of invoices from the database.
     *
     * @param page      The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @return A PaginationDto containing data about the retrieved items
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Invoice> getAllInvoices(int page, int pageCount);

    /**
     * Returns amount of invoices in the database.
     *
     * @return The amount of invoices in the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    long getInvoiceCount();


    /**
     * Create new invoice.
     *
     * @param invoice is the new invoice to create
     * @return new invoice
     * @throws ServiceException upon encountering errors with the database
     */
    Invoice createInvoice(Invoice invoice);



}
