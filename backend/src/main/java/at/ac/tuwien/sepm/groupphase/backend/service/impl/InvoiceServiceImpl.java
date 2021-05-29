package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceRepository invoiceRepository;
    private final Validator validator;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, Validator validator) {
        this.invoiceRepository = invoiceRepository;
        this.validator = validator;
    }


    @Override
    public List<Invoice> findAllInvoices() {
        LOGGER.trace("Find all invoices");
        try {
            return this.invoiceRepository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("Could not find any invoices", e);
        }
    }


    @Override
    public Invoice findOneById(Long id) {
        LOGGER.trace("Find invoices with id {}", id);
        Invoice invoice;
        try {
            Optional<Invoice> invoiceOptional = this.invoiceRepository.findById(id);
            invoice = invoiceOptional.orElse(null);
        } catch (NotFoundException e) {
            LOGGER.error("Problem while creating Inovice", e);
            throw new NotFoundException(String.format("Could not find invoice with id %s", id));
        }
        return invoice;

    }


    @Transactional
    @Override
    public Invoice creatInvoice(Invoice invoice) {
        LOGGER.trace("Create invoice {}", invoice);
        validator.validateNewInvoice(invoice);
        try {
            return this.invoiceRepository.save(invoice);
        } catch (DataAccessException e) {
            LOGGER.error("Problem while creating Inovice", e);
            throw new ServiceException("Problem while creating Inovice", e);
        }
    }

}
