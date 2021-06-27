package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
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
        return pdfGenerator.generatePdf(invoice, null);
    }


    @Override
    public byte[] createPdfInvoiceCustomerFromInvoice(Invoice invoice) {
        LOGGER.trace("createPdfInvoiceCustomerFromInvoice({})", invoice);
        Order order = this.orderService.getOrderByInvoice(invoice);
        return pdfGenerator.generatePdf(order.getInvoice(), order);
    }

    @Override
    public byte[] createPdfInvoiceCustomer(Order order) {
        LOGGER.trace("createPdfInvoiceCustomer({})", order);
        return pdfGenerator.generatePdf(order.getInvoice(), order);
    }


    @Override
    public byte[] createPdfCanceledInvoiceOperator(Invoice invoice) {
        LOGGER.trace("createPdfCanceledInvoiceOperator({})", invoice);
        if (invoice.getInvoiceType() != InvoiceType.canceled) {
            throw new ServiceException("Diese Rechnung kann nicht storniert werden");
        }
        if (invoice.getCustomerId() == null) {
            return pdfGenerator.generatePdf(invoice, null);
        }
        return pdfGenerator.generatePdf(invoice, this.orderService.getOrderByInvoice(invoice));
    }

    @Override
    public byte[] createPdfCanceledInvoiceCustomer(Order order) {
        LOGGER.trace("createPdfCanceledInvoiceCustomer({})", order);
        if (order.getInvoice().getInvoiceType() != InvoiceType.canceled) {
            throw new ServiceException("Diese Rechnung kann nicht storniert werden");
        }
        return pdfGenerator.generatePdf(order.getInvoice(), order);
    }
}