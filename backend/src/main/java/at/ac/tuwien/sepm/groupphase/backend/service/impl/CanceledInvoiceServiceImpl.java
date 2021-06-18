package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CanceledInvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CanceledInvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CanceledInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class CanceledInvoiceServiceImpl implements CanceledInvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CanceledInvoiceRepository canceledInvoiceRepository;
    private final CanceledInvoiceItemRepository canceledInvoiceItemRepository;

    @Autowired
    public CanceledInvoiceServiceImpl(CanceledInvoiceRepository canceledInvoiceRepository, CanceledInvoiceItemRepository canceledInvoiceItemRepository) {
        this.canceledInvoiceRepository = canceledInvoiceRepository;
        this.canceledInvoiceItemRepository = canceledInvoiceItemRepository;
    }

    @Override
    public CanceledInvoice createCanceledInvoiceByInvoice(Invoice invoice) {
        if (invoice.getInvoiceType() == InvoiceType.canceled) {

            CanceledInvoice canceledInvoice = new CanceledInvoice();
            canceledInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
            canceledInvoice.setCustomerId(invoice.getCustomerId());
            Set<CanceledInvoiceItem> canceledInvoiceItems = new HashSet<>();
            double subtotal = 0;
            double taxAmount = 0;
            double total = 0;
            for (InvoiceItem item : invoice.getItems()) {
                double price = item.getProduct().getPrice();
                int numberOfItems = item.getNumberOfItems();
                double tax = item.getProduct().getTaxRate().getCalculationFactor();
                subtotal += (price * numberOfItems);
                taxAmount += price * numberOfItems * (tax - 1);
                total = subtotal + taxAmount;
                canceledInvoiceItems.add(this.canceledInvoiceItemRepository.save(new CanceledInvoiceItem(numberOfItems, item.getProduct().getName(), item.getProduct().getTaxRate().getPercentage(), item.getProduct().getPrice())));
            }
            canceledInvoice.setCanceledInvoiceItems(canceledInvoiceItems);
            canceledInvoice.setSubtotal(subtotal);
            canceledInvoice.setTaxAmount(taxAmount);
            canceledInvoice.setTotal(total);
            this.canceledInvoiceRepository.save(canceledInvoice);
        } else {
            throw new ServiceException("This invoice is not declared as canceled");
        }
        return null;
    }

    @Override
    public CanceledInvoice createCanceledInvoice(CanceledInvoice canceledInvoice) {
        canceledInvoice.setDate(LocalDateTime.now());
        return this.canceledInvoiceRepository.save(canceledInvoice);
    }

    @Override
    @Cacheable(value = "invoicePages")
    public Page<CanceledInvoice> findAll(int page, int pageCount) {
        LOGGER.trace("findAll({},{},{})", page, pageCount);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return this.canceledInvoiceRepository.findAll(returnPage);
    }


    @Override
    @Cacheable(value = "counts", key = "'canceledInvoices'")
    public Long getCanceledInvoiceCount() {
        LOGGER.trace("getInvoiceCount()");
        return this.canceledInvoiceRepository.count();
    }
}
