package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
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
    private final OrderService orderService;

    @Autowired
    public PdfGeneratorServiceImpl(PdfGenerator pdfGenerator, OrderService orderService) {
        this.pdfGenerator = pdfGenerator;
        this.orderService = orderService;
    }


    @Override
    public byte[] createPdfInvoiceOperator(Invoice invoice) {
        LOGGER.trace("createPdfInvoiceOperator({})", invoice);
        return pdfGenerator.generatePdfOperator(invoice);
    }


    @Override
    public byte[] createPdfInvoiceCustomerFromInvoice(Invoice invoice) {
        LOGGER.trace("createPdfInvoiceCustomerFromInvoice({})", invoice);
        return pdfGenerator.generatePdfCustomer(this.orderService.getOrderByInvoice(invoice));
    }

    @Override
    public byte[] createPdfInvoiceCustomer(Order order) {
        LOGGER.trace("createPdfInvoiceCustomer({})", order);
        return pdfGenerator.generatePdfCustomer(order);
    }

    @Override
    public byte[] createPdfCanceledInvoiceOperator(CanceledInvoice canceledInvoiceService) {
        LOGGER.trace("createPdfInvoiceCustomer({})", canceledInvoiceService);
        return pdfGenerator.generatePdfOperatorCanceled(canceledInvoiceService);
    }
}