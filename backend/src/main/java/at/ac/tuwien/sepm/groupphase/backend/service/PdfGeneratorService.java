package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.ShopSettings;

/**
 * A service class generates pdf files from saved invoices.
 */
public interface PdfGeneratorService {

    /**
     * Creates a invoice pdf for operators from a given invoice.
     *
     * @param invoice the invoice to be create as pdf
     * @return byte array includes pdf.
     */
    byte[] createPdfInvoiceOperator(Invoice invoice);

    /**
     * Creates a canceled invoice pdf for operators from a given invoice.
     *
     * @param invoice to generate a pdf of it
     * @return byte array canceled invoice pdf.
     */
    byte[] createPdfCanceledInvoiceOperator(Invoice invoice);

    /**
     * Creates a canceled invoice pdf of a customer for operator from a given invoice.
     *
     * @param invoice the invoice to be create as pdf
     * @return byte array includes pdf.
     */
    byte[] createPdfInvoiceCustomerFromInvoice(Invoice invoice);

    /**
     * Creates a canceled invoice pdf for customers from a given order.
     *
     * @param order to generate a pdf of it
     * @return byte array includes pdf.
     */
    byte[] createPdfInvoiceCustomer(Order order);


    /**
     * Updates the company information in the pdf invoice.
     *
     * @param shopSettings the updates company information
     */
    void updateCompanyInformation(ShopSettings shopSettings);

    /**
     * Recreates an invoice pdf to a canceled invoice pdf.
     *
     * @param invoice to recreates the pdf
     */
    void setPdfInvoiceCanceled(Invoice invoice);


    /**
     * Recreates an invoice pdf of an order to a canceled invoice pdf.
     *
     * @param order to recreates the invoice pdf
     */
    void setPdfOrderCanceled(Order order);


}
