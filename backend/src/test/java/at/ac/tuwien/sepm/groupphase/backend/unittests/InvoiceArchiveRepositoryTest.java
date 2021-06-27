package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceArchive;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceArchivedRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InvoiceArchiveRepositoryTest implements TestData {
    private final InvoiceArchive invoiceArchive = new InvoiceArchive();

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceArchivedRepository invoiceArchivedRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @BeforeEach
    public void beforeEach() {
        InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
        InvoiceItem invoiceItem = new InvoiceItem();
        Invoice newInvoice = new Invoice();
        Product product = new Product();
        Category category = new Category();
        TaxRate taxRate = new TaxRate();
        invoiceRepository.deleteAll();
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        category.setId(1L);
        category.setName(TEST_CATEGORY_NAME);

        taxRate.setId(1L);
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);
        taxRate.setPercentage((TEST_TAX_RATE_PERCENTAGE/100) + 1);

        // product
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);
        product.setTaxRate(taxRateRepository.save(taxRate));
        product.setCategory(categoryRepository.save(category));

        // invoiceItem
        invoiceItemKey.setInvoiceId(null);
        invoiceItemKey.setProductId(product.getId());

        invoiceItem.setId(invoiceItemKey);
        invoiceItem.setProduct(productRepository.save(product));
        invoiceItem.setNumberOfItems(10);

        // invoiceItem to invoice
        Set<InvoiceItem> invoiceItemSet = new HashSet<>();
        invoiceItemSet.add(invoiceItem);
        newInvoice.setItems(invoiceItemSet);
        newInvoice.setInvoiceNumber(TEST_INVOICE_NUMBER_1);
        newInvoice.setDate(LocalDateTime.now());
        newInvoice.setAmount(TEST_INVOICE_AMOUNT);
        newInvoice.setInvoiceType(InvoiceType.operator);
        String invoiceNumber = newInvoice.getInvoiceNumber();
        byte[] pdf = pdfGeneratorService.createPdfInvoiceOperator(newInvoice);
        invoiceArchive.setInvoiceNumber(invoiceNumber);
        invoiceArchive.setInvoiceAsPdf(pdf);
    }

    @Test
    public void createNewInvoiceArchived_thenReturnInvoiceArchive() {
        invoiceArchivedRepository.deleteAll();
        InvoiceArchive archive = invoiceArchivedRepository.save(this.invoiceArchive);
        assertEquals(this.invoiceArchive, archive);
    }
}
