package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;

import at.ac.tuwien.sepm.groupphase.backend.util.InvoiceSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class InvoiceRepositoryTest implements TestData {
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;


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
    public void givenAllProperties_whenSaveInvoice_thenFindListWithOneInvoiceAndFindElementById() {
        invoiceItem.setInvoice(invoiceRepository.save(invoice));
        invoiceItemRepository.save(invoiceItem);
        Pageable returnPage = PageRequest.of(0, 15);
        assertAll(
            () -> assertEquals(1, invoiceRepository.findAll().size()),
            () -> assertNotNull(invoiceRepository.findById(invoice.getId())),
            () -> assertNotNull(invoiceRepository.findAll(InvoiceSpecifications.hasInvoiceType(InvoiceType.operator), returnPage).getContent()),
            () -> assertEquals(1, invoiceRepository.findAll(InvoiceSpecifications.hasInvoiceType(InvoiceType.operator), returnPage).getContent().size())
        );
    }

    @Test
    public void givenAllProperties_whenGetInvoice_thenFindListWithOneInvoiceAndFindElementById() {
        invoiceItem.setInvoice(invoiceRepository.save(invoice));
        invoiceItemRepository.save(invoiceItem);
        Pageable returnPage = PageRequest.of(0, 15);
        assertAll(
            () -> assertEquals(1, invoiceRepository.findAll(returnPage).getContent().size())
        );
    }

    @Test
    public void givenAllProperties_whenGetInvoice_thenFindListWIthOneInvoiceWithTheCustomer(){
        //First Address for customer
        Address address = new Address(TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
        addressRepository.save(address);

        //Then Customer
        Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");
        Customer newCustomer = customerRepository.save(customer);
        //Then Invoice
        Invoice newInvoice = invoiceRepository.save(invoice);
        invoiceItem.setInvoice(newInvoice);
        invoiceItemRepository.save(invoiceItem);

        assertAll(
            () -> assertNotNull(invoiceRepository.findByIdAndCustomerId(newInvoice.getId(), newCustomer.getId())),
            () -> assertEquals(Optional.empty(),invoiceRepository.findByIdAndCustomerId(newInvoice.getId(), -100L))
        );

    }
}
