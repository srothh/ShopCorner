package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceRepository invoiceRepository;
    private final Validator validator;
    private final InvoiceItemService invoiceItemService;


    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, Validator validator, InvoiceItemService invoiceItemService) {
        this.invoiceRepository = invoiceRepository;
        this.validator = validator;
        this.invoiceItemService = invoiceItemService;
    }


    @Override
    public List<Invoice> findAllInvoices() {
        LOGGER.trace("findAllInvoices()");
        return this.invoiceRepository.findAll();

    }

    @Override
    public Page<Invoice> getAllInvoices(int page, int pageCount) {
        LOGGER.trace("getAllCustomers()");
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return invoiceRepository.findAll(returnPage);
    }

    @Cacheable(value = "counts", key = "'customers'")
    @Override
    public long getInvoiceCount() {
        return invoiceRepository.count();
    }

    @Override
    public Invoice findOneById(Long id) {
        LOGGER.trace("findOneById({})", id);
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Could not find invoice with id %s", id)));
        return invoice;

    }


    @Transactional
    @Override
    public Invoice createInvoice(Invoice invoice) {
        LOGGER.trace("createInvoice({})", invoice);
        validator.validateNewInvoice(invoice);
        validator.validateNewInvoiceItem(invoice.getItems());
        Set<InvoiceItem> items = invoice.getItems();
        invoice.setItems(null);
        Invoice createdInvoice = this.invoiceRepository.save(invoice);
        for (InvoiceItem item : items) {
            item.setInvoice(createdInvoice);
        }
        createdInvoice.setItems(invoiceItemService.createInvoiceItem(items));
        return createdInvoice;

    }
}
