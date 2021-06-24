package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;

/**
 * A service class handling Invoices.
 */
public interface InvoiceService {

    /**
     * Find a single invoice entry by id.
     *
     * @param id the id of the invoice entry
     * @return the invoice entry
     * @throws NotFoundException when no invoice with the id is found
     */
    Invoice findOneById(Long id);

    /**
     * Returns amount of invoices in the database.
     *
     * @return The amount of invoices in the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Long getInvoiceCount();

    /**
     * Updates the given invoice in the database to canceled.
     *
     * @param invoice is the invoice to be canceled
     * @return The canceled invoice from the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Invoice setInvoiceCanceled(Invoice invoice);

    /**
     * Returns amount of customerInvoices in the database.
     *
     * @return The amount of customer invoices in the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Long getCustomerInvoiceCount();

    /**
     * Returns amount of canceledInvoices in the database.
     *
     * @return The amount of canceled invoices in the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Long getCanceledInvoiceCount();

    /**
     * Create new invoice.
     *
     * @param invoice is the new invoice to create
     * @return new invoice
     * @throws ServiceException upon encountering errors with the database
     */
    Invoice createInvoice(Invoice invoice);

    /**
     * Returns page with all needed Invoices.
     *
     * @param page        which should be returned
     * @param invoiceType of invoices which should be returned
     * @param pageCount   amount of invoices per page
     * @return Page with all Invoices with right permission
     */
    Page<Invoice> findAll(int page, int pageCount, InvoiceType invoiceType);

}
