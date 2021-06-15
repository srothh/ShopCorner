package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

public interface PdfGeneratorService {
    byte[] createPdfInvoiceOperator(Invoice invoice);
}
