package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class InvoiceItemRepositoryTest implements TestData {
    private final InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
    private final InvoiceItem invoiceItem = new InvoiceItem();
    private final Invoice invoice = new Invoice();
    private final Product product = new Product();
    private final Category category = new Category();
    private final TaxRate taxRate = new TaxRate();

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @BeforeEach
    public void beforeEach() {
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        category.setId(1L);
        category.setName(TEST_CATEGORY_NAME);

        taxRate.setId(1L);
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);

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
        invoice.setInvoiceNumber(TEST_INVOICE_NUMBER_1);
        invoice.setDate(LocalDateTime.now());
        invoice.setAmount(TEST_INVOICE_AMOUNT);
        invoice.setInvoiceType(InvoiceType.operator);

    }


    @Test
    public void givenAllProperties_whenSaveInvoiceItem_thenFindListWithOneInvoiceAndFindElementById() {

        invoiceItem.setInvoice(invoiceRepository.save(invoice));
        invoiceItemRepository.save(invoiceItem);

        assertAll(
            () -> assertEquals(1, invoiceRepository.findAll().size()),
            () -> assertNotNull(invoiceRepository.findById(invoice.getId()))
        );

    }
}
