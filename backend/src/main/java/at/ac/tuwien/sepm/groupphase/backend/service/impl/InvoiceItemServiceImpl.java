package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public List<InvoiceItem> findAllInvoices() {
        LOGGER.debug("Find all invoices items");
        return this.invoiceItemRepository.findAll();
    }

    @Override
    public Set<InvoiceItem> creatInvoiceItem(Set<InvoiceItem> invoiceItem) {
        LOGGER.debug("Create invoice {}", invoiceItem);
        for (InvoiceItem item : invoiceItem) {
            if (item != null) {
                this.invoiceItemRepository.save(item);

            }
        }
        return null;
    }
}
