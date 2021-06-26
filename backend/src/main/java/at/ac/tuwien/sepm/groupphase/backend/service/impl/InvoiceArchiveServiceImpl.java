package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceArchivedRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceArchiveService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceArchiveServiceImpl implements InvoiceArchiveService {
    private final InvoiceArchivedRepository invoiceArchivedRepository;

    public InvoiceArchiveServiceImpl(InvoiceArchivedRepository invoiceArchivedRepository) {
        this.invoiceArchivedRepository = invoiceArchivedRepository;
    }

    @Override
    public byte[] findByInvoiceNumber(String invoiceNumber) {
        InvoiceArchive archive = invoiceArchivedRepository.findInvoiceArchiveByInvoiceNumber(invoiceNumber).orElseThrow(() -> new NotFoundException("Could not find invoice"));
        return archive.getInvoiceAsPdf();
    }

    @Override
    public boolean invoiceExistsByInvoiceNumber(String invoiceNumber) {
        return invoiceArchivedRepository.existsInvoiceArchiveByInvoiceNumber(invoiceNumber);
    }

    @Override
    public byte[] createInvoiceArchive(String id, byte[] content) {
        InvoiceArchive invoiceArchive = new InvoiceArchive();
        invoiceArchive.setInvoiceAsPdf(content);
        invoiceArchive.setInvoiceNumber(id);

        return this.invoiceArchivedRepository.save(invoiceArchive).getInvoiceAsPdf();
    }
}
