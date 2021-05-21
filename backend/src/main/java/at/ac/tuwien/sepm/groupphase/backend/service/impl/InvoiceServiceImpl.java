package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


    @Override
    public List<Invoice> findAllInvoices() {
        LOGGER.debug("Find all invoices");
        return this.invoiceRepository.findAll();
    }

    @Override
    public Invoice findOneById(Long id) {
        LOGGER.debug("Find invoices with id {}", id);
        Optional<Invoice> invoice = this.invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            return invoice.get();
        } else {
            throw new NotFoundException(String.format("Could not find message with id %s", id));
        }
    }

    @Override
    public Invoice findOneByDate(LocalDateTime date) {
        LOGGER.debug("Find invoices with id {}", date);
        Invoice invoice = this.invoiceRepository.findByDate(date);
        if (invoice != null) {
            return invoice;
        } else {
            throw new NotFoundException(String.format("Could not find message with date %s", date.toString()));
        }
    }

    @Override
    public Invoice creatInvoice(Invoice invoice) {
        LOGGER.debug("Create invoice {}", invoice);
        return this.invoiceRepository.save(invoice);
    }

}
