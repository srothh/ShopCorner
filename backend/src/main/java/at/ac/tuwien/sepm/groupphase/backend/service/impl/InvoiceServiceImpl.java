package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.InvoiceSpecifications;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceRepository invoiceRepository;
    private final Validator validator;
    private final InvoiceItemService invoiceItemService;


    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, Validator validator, InvoiceItemService invoiceItemService) {
        this.invoiceRepository = invoiceRepository;
        this.validator = validator;
        this.invoiceItemService = invoiceItemService;
    }


    @Override
    @Cacheable(value = "invoicePages")
    public Page<Invoice> findAll(int page, int pageCount, InvoiceType invoiceType) {
        LOGGER.trace("findAll({},{},{})", page, pageCount, invoiceType);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount, Sort.by("date").descending());
        if (invoiceType == InvoiceType.operator) {
            return this.invoiceRepository.findAll(returnPage);
        }
        return this.invoiceRepository.findAll(InvoiceSpecifications.hasInvoiceType(invoiceType), returnPage);
    }

    @Override
    public List<Invoice> findByDate(LocalDateTime start, LocalDateTime end) {
        LOGGER.trace("findByDate({}{})", start, end);
        return invoiceRepository.findAll(InvoiceSpecifications.isInPeriod(start, end));
    }

    @Override
    @Cacheable(value = "counts", key = "'invoices'")
    public Long getInvoiceCount() {
        LOGGER.trace("getInvoiceCount()");
        return invoiceRepository.count();
    }

    @Override
    @Cacheable(value = "counts", key = "'customerInvoices'")
    public Long getCustomerInvoiceCount() {
        LOGGER.trace("getCustomerInvoiceCount()");
        return invoiceRepository.count(InvoiceSpecifications.hasInvoiceType(InvoiceType.customer));
    }

    @Override
    @Cacheable(value = "counts", key = "'canceledInvoices'")
    public Long getCanceledInvoiceCount() {
        LOGGER.trace("getCanceledInvoiceCount()");
        return invoiceRepository.count(InvoiceSpecifications.hasInvoiceType(InvoiceType.canceled));
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'invoices'"),
        @CacheEvict(value = "counts", key = "'canceledInvoices'"),
        @CacheEvict(value = "invoicePages", allEntries = true)
    })
    public Invoice setInvoiceCanceled(Invoice invoice) {
        LOGGER.trace("setInvoiceCanceled({})", invoice);
        invoice.setInvoiceType(InvoiceType.canceled);
        return this.invoiceRepository.save(invoice);
    }

    @Cacheable(value = "counts", key = "'invoicesByYear'")
    public long getInvoiceCountByYear(LocalDateTime firstDateOfYear) {
        LOGGER.trace("getInvoiceCountByYear()");
        return invoiceRepository.countInvoiceByDateAfter(firstDateOfYear);
    }

    @Override
    public Invoice findOneById(Long id) {
        LOGGER.trace("findOneById({})", id);
        return this.invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Rechnung konnte nicht gefunden werden"));
    }

    @Override
    public Invoice findOneByOrderNumber(String orderNumber) {
        return this.invoiceRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new NotFoundException("Rechnung konnte nicht gefunden werden"));
    }

    @Override
    public Invoice getByIdAndCustomerId(Long id, Long customerId) {
        LOGGER.trace("getByIdAndCustomerId({}, {})", id, customerId);
        return this.invoiceRepository.findByIdAndCustomerId(id, customerId)
            .orElseThrow(() -> new NotFoundException(String.format("Rechnung mit id %d konnte nicht gefunden werden", id)));
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'invoices'"),
        @CacheEvict(value = "counts", key = "'customerInvoices'"),
        @CacheEvict(value = "counts", key = "'invoicesByYear'"),
        @CacheEvict(value = "invoicePages", allEntries = true)
    })
    @Transactional
    @Override
    public Invoice createInvoice(Invoice invoice) {
        LOGGER.trace("createInvoice({})", invoice);
        validator.validateNewInvoice(invoice);
        validator.validateNewInvoiceItem(invoice.getItems());
        Set<InvoiceItem> items = invoice.getItems();
        invoice.setItems(null);
        LocalDateTime firstDateOfYear = LocalDateTime.now().toLocalDate().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
        invoice.setInvoiceNumber((this.getInvoiceCountByYear(firstDateOfYear) + 1) + "" + invoice.getDate().getYear());
        Invoice createdInvoice = this.invoiceRepository.save(invoice);
        for (InvoiceItem item : items) {
            item.setInvoice(createdInvoice);
        }
        createdInvoice.setItems(invoiceItemService.createInvoiceItem(items));
        return createdInvoice;

    }

}
