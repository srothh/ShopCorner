package at.ac.tuwien.sepm.groupphase.backend.service;

public interface InvoiceArchiveService {

    byte[] findByInvoiceNumber(String invoiceNumber);

    boolean invoiceExistsByInvoiceNumber(String invoiceNumber);

    byte[] createInvoiceArchive(String id, byte[] content);
}
