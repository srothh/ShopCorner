package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InvoiceMappingTest implements TestData {
    private static Invoice invoice = new Invoice();


    @Autowired
    private InvoiceMapper invoiceMapping;


    @BeforeAll
    static void beforeAll() {
        InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
        InvoiceItem invoiceItem = new InvoiceItem();
        Product product = new Product();
        Category category = new Category();
        TaxRate taxRate = new TaxRate();
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

        // invoiceItem
        invoiceItemKey.setInvoiceId(null);
        invoiceItemKey.setProductId(product.getId());

        invoiceItem.setId(invoiceItemKey);
        invoiceItem.setNumberOfItems(10);

        // invoiceItem to invoice
        Set<InvoiceItem> items = new HashSet<>();
        items.add(invoiceItem);
        invoice.setId(TEST_INVOICE_ID);
        invoice.setDate(LocalDateTime.now());
        invoice.setAmount(TEST_INVOICE_AMOUNT);
        invoice.setItems(items);

    }

    @Test
    public void givenAllProperties_whenMapSimpleInvoiceDtoToEntity_thenEntityHasAllProperties() {
        SimpleInvoiceDto invoiceDto = invoiceMapping.invoiceToSimpleInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, invoiceDto.getId()),
            () -> assertEquals(invoice.getDate(), invoiceDto.getDate()),
            () -> assertEquals(TEST_INVOICE_AMOUNT, invoiceDto.getAmount())
        );
    }


    @Test
    public void givenAllProperties_whenMapListWithTwoSimpleInvoiceDtoToDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        invoiceList.add(invoice);

        List<SimpleInvoiceDto> simpleInvoiceDtos = invoiceMapping.invoiceToSimpleInvoiceDto(invoiceList);
        assertEquals(2, simpleInvoiceDtos.size());
        SimpleInvoiceDto simpleInvoiceDto = simpleInvoiceDtos.get(0);
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, simpleInvoiceDto.getId()),
            () -> assertEquals(invoice.getDate(), simpleInvoiceDto.getDate()),
            () -> assertEquals(TEST_INVOICE_AMOUNT, simpleInvoiceDto.getAmount())
        );
    }

    @Test
    public void givenAllProperties_whenMapDetailedInvoiceDtoToEntity_thenEntityHasAllProperties() {
        DetailedInvoiceDto invoiceDto = invoiceMapping.invoiceToDetailedInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, invoiceDto.getId()),
            () -> assertEquals(invoice.getDate(), invoiceDto.getDate()),
            () -> assertEquals(TEST_INVOICE_AMOUNT, invoiceDto.getAmount())
        );
    }



}
