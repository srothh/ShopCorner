package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

/**
 * A service class generates pdf files from saved invoices.
 */
public interface PdfGeneratorService {

    /**
     * Creates a pdf for operators from a given invoice.
     *
     * @param invoice the invoice to be create as pdf
     * @return byte array includes pdf.
     */
    byte[] createPdfInvoiceOperator(Invoice invoice);


    /**
     * Creates a pdf for customers from a given invoice.
     *
     * @param invoice the invoice to be create as pdf
     * @return byte array includes pdf.
     */
    byte[] createPdfInvoiceCustomer(Invoice invoice);
}
