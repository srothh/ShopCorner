package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

/**
 * A service class generates pdf files from saved invoices.
 */
public interface PdfGeneratorService {
    byte[] createPdfInvoiceOperator(Invoice invoice);
}
