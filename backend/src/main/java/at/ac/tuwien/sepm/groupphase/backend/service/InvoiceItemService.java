package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;

import java.util.List;
import java.util.Set;

public interface InvoiceItemService {

    List<InvoiceItem> findAllInvoices();

    Set<InvoiceItem> creatInvoiceItem(Set<InvoiceItem> invoiceItem);
}
