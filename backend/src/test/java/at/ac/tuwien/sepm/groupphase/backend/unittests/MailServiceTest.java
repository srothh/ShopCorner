package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MailServiceTest implements TestData {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
        .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "test"))
        .withPerMethodLifecycle(false);

    @Autowired
    private MailService mailService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ProductRepository productRepository;

    private final Address address = new Address(TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
    private final Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");

    private final InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
    private final InvoiceItem invoiceItem = new InvoiceItem();
    private final Invoice invoice = new Invoice();
    private final Product product = new Product();
    private final Category category = new Category();
    private final TaxRate taxRate = new TaxRate();
    private final Order order = new Order();

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        invoiceItemRepository.deleteAll();
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        category.setId(1L);
        category.setName(TEST_CATEGORY_NAME);

        taxRate.setId(1L);
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);
        taxRate.setPercentage(1.2);

        // product
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);
        /*product.setTaxRate(taxRate);
        product.setCategory(category);
*/
        product.setTaxRate(taxRateRepository.save(taxRate));
        product.setCategory(category);

        // invoiceItem
        invoiceItemKey.setInvoiceId(null);
        invoiceItemKey.setProductId(product.getId());

        invoiceItem.setId(invoiceItemKey);
        invoiceItem.setProduct(product);
        invoiceItem.setNumberOfItems(10);

        // invoiceItem to invoice
        Set<InvoiceItem> invoiceItemSet = new HashSet<>();
        invoiceItemSet.add(invoiceItem);
        invoice.setItems(invoiceItemSet);
        invoice.setInvoiceNumber(TEST_INVOICE_NUMBER_1);
        invoice.setDate(LocalDateTime.now());
        invoice.setAmount(TEST_INVOICE_AMOUNT);
        invoice.setInvoiceType(InvoiceType.operator);

        order.setCustomer(customer);
        order.setInvoice(invoice);
    }

    @Test
    public void sendsMailToCustomer() {
        mailService.sendMail(order);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertAll(
            () -> assertNotNull(receivedMessage),
            () -> assertEquals(1, receivedMessage.getAllRecipients().length),
            () -> assertEquals(customer.getEmail(),receivedMessage.getAllRecipients()[0].toString())
        );
    }
}
