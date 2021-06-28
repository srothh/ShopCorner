package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceArchivedRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class InvoiceArchiveServiceImpl implements InvoiceArchiveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceArchivedRepository invoiceArchivedRepository;

    public InvoiceArchiveServiceImpl(InvoiceArchivedRepository invoiceArchivedRepository) {
        this.invoiceArchivedRepository = invoiceArchivedRepository;
    }

    @Override
    public byte[] findByInvoiceNumber(String invoiceNumber) {
        LOGGER.trace("findByInvoiceNumber({})", invoiceNumber);
        InvoiceArchive archive = invoiceArchivedRepository.findInvoiceArchiveByInvoiceNumber(invoiceNumber).orElseThrow(() -> new NotFoundException("Rechnung konnte nicht gefunden werden."));
        return archive.getInvoiceAsPdf();
    }

    @Override
    public boolean invoiceExistsByInvoiceNumber(String invoiceNumber) {
        LOGGER.trace("invoiceExistsByInvoiceNumber({})", invoiceNumber);
        return invoiceArchivedRepository.existsInvoiceArchiveByInvoiceNumber(invoiceNumber);
    }

    @Override
    public byte[] createInvoiceArchive(String invoiceNumber, byte[] content) {
        LOGGER.trace("createInvoiceArchive({},{})", invoiceNumber, content);
        InvoiceArchive invoiceArchive = new InvoiceArchive();
        invoiceArchive.setInvoiceAsPdf(content);
        invoiceArchive.setInvoiceNumber(invoiceNumber);
        return this.invoiceArchivedRepository.save(invoiceArchive).getInvoiceAsPdf();
    }

    @Override
    public void updateInvoiceArchive(String invoiceNumber, byte[] content) {
        LOGGER.trace("createInvoiceArchive({},{})", invoiceNumber, content);
        InvoiceArchive invoiceArchive = this.invoiceArchivedRepository.findInvoiceArchiveByInvoiceNumber(invoiceNumber).orElseThrow(() -> new NotFoundException("Rechnung konnte nicht gefunden werden."));
        invoiceArchive.setInvoiceAsPdf(content);
        this.invoiceArchivedRepository.save(invoiceArchive);
    }
}
