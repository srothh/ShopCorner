package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.springframework.data.domain.Page;

public interface CanceledInvoiceService {

    CanceledInvoice createCanceledInvoiceByInvoice(Invoice invoice);

    CanceledInvoice createCanceledInvoice(CanceledInvoice canceledInvoice);

    /**
     * Returns amount of canceledInvoices in the database.
     *
     * @return The amount of canceledInvoices in the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Long getCanceledInvoiceCount();

    /**
     * Returns page with all needed invoices.
     *
     * @param page        which should be returned
     * @param pageCount   amount of operators per page
     * @return Page with all Operators with right permission
     */
    Page<CanceledInvoice> findAll(int page, int pageCount);

}
