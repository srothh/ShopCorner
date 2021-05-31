package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;

import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceItemRepository invoiceItemRepository;
    private final Validator validator;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository, Validator validator) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.validator = validator;
    }

    @Override
    public List<InvoiceItem> findAllInvoicesItems() {
        LOGGER.trace("findAllInvoicesItems()");
        return this.invoiceItemRepository.findAll();

    }

    @Override
    public Set<InvoiceItem> createInvoiceItem(Set<InvoiceItem> invoiceItems) {
        LOGGER.trace("createInvoiceItem({})", invoiceItems);
        validator.validateNewInvoiceItem(invoiceItems);
        Set<InvoiceItem> newInvoiceItems = new HashSet<>();
        for (InvoiceItem item : invoiceItems) {
            if (item != null || item.getId() != null) {
                newInvoiceItems.add(this.invoiceItemRepository.save(item));
            }
        }
        return newInvoiceItems;
    }
}
