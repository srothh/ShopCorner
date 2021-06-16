package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import at.ac.tuwien.sepm.groupphase.backend.util.PdfGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PdfGenerator pdfGenerator;

    @Autowired
    public PdfGeneratorServiceImpl(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }


    @Override
    public byte[] createPdfInvoiceOperator(Invoice invoice) {
        LOGGER.trace("createPdfInvoiceOperator({})", invoice);
        return pdfGenerator.generatePdfOperator(invoice);
    }

    @Override
    public byte[] createPdfInvoiceCustomer( Invoice invoice) {
        LOGGER.trace("createPdfInvoiceCustomer({})", invoice);
        Customer customer = null;
        return pdfGenerator.generatePdfCustomer(customer, invoice);
    }
}