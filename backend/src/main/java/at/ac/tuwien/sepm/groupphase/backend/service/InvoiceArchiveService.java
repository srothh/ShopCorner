package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;

public interface InvoiceArchiveService {

    byte[] findByInvoiceNumber(String invoiceNumber);

    boolean invoiceExistsByInvoiceNumber(String invoiceNumber);

    byte[] createInvoiceArchive(String id, byte[] content);
}
