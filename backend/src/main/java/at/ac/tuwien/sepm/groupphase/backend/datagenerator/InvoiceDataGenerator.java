package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;


@Profile("generateData")
@Component
public class InvoiceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceDataGenerator(ProductRepository productRepository, InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;

    }

    @PostConstruct
    public void generateInvoices() {
        if (invoiceRepository.findAll().size() > 0) {
            LOGGER.debug("operators already generated");
        } else {
            for (int i = 1; i <= 50; i++) {
                InvoiceItemKey itemId = new InvoiceItemKey();
                InvoiceItem item = new InvoiceItem();
                Invoice invoice = new Invoice();

                Product p = productRepository.findAll().get(0);
                item.setProduct(p);
                item.setNumberOfItems(i);
                invoice.setDate(LocalDateTime.now().minus(i, ChronoUnit.DAYS));
                invoice.setAmount((item.getNumberOfItems() * i));
                Invoice newInvoice = invoiceRepository.save(invoice);
                item.setInvoice(newInvoice);
                itemId.setInvoiceId(newInvoice.getId());
                item.setId(itemId);
                Set<InvoiceItem> itemSet = new HashSet<>();
                itemSet.add(item);
                invoice.setItems(itemSet);
                invoiceItemRepository.save(item);
            }
        }
    }
}