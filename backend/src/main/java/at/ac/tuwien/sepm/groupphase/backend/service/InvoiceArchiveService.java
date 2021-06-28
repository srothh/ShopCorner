package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

public interface InvoiceArchiveService {

    /**
     * Find a single invoice entry by id.
     *
     * @param invoiceNumber the invoice number of the invoice entry
     * @return the invoice pdf as byte array
     * @throws NotFoundException when no invoice with the invoice number is found
     */
    byte[] findByInvoiceNumber(String invoiceNumber);

    /**
     * Find a single invoiceArchive by the invoiceNumber.
     *
     * @param invoiceNumber the invoiceNumber of the invoice
     * @return true if invoice is already in the database
     * @throws RuntimeException when no invoice with the invoiceNumber is found
     */
    boolean invoiceExistsByInvoiceNumber(String invoiceNumber);

    /**
     * Create new invoiceArchive entry.
     *
     * @param invoiceNumber is the new invoiceNumber of the invoice to create
     * @param content is the created pdf invoice
     * @return pdf content as byte array
     * @throws ServiceException upon encountering errors with the database
     */
    byte[] createInvoiceArchive(String invoiceNumber, byte[] content);


    /**
     * Updates the invoice in the archive to canceled.
     *
     * @param invoiceNumber the number of the invoice to be updated
     * @param content the new content of the invoice
     * @throws RuntimeException upon encountering errors with the database
     */
    void updateInvoiceArchive(String invoiceNumber, byte[] content);
}
