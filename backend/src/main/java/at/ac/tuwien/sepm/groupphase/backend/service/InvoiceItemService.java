package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;
import java.util.Set;

public interface InvoiceItemService {

    /**
     * Returns all invoiceItems from the database.
     *
     * @return A list containing all the invoiceItems entities from the database
     * @throws NotFoundException if no data was found in the database
     */
    List<InvoiceItem> findAllInvoicesItems();

    /**
     * Adds a new invoice item to the database.
     *
     * @param invoiceItem The invoice item entity to add to the database
     * @return The added invoice item entity
     * @throws RuntimeException upon encountering errors with the database
     */
    Set<InvoiceItem> creatInvoiceItem(Set<InvoiceItem> invoiceItem);
}
