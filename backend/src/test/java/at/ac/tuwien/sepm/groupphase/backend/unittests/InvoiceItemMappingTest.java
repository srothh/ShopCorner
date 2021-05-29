package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
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


import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InvoiceItemMappingTest implements TestData {
        private static InvoiceItem invoiceItem = new InvoiceItem();
        private static InvoiceItem invoiceItem2 = new InvoiceItem();


    @Autowired
    private InvoiceItemMapper invoiceItemMapper;


    @BeforeAll
    static void beforeAll() {
        InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
        InvoiceItemKey invoiceItemKey2 = new InvoiceItemKey();
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

        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        // invoiceItem
        invoiceItemKey.setInvoiceId(null);
        invoiceItemKey.setProductId(product.getId());


        invoiceItem.setId(invoiceItemKey);
        invoiceItem.setNumberOfItems(10);

        product.setId(1L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        invoiceItemKey2.setInvoiceId(null);
        invoiceItemKey2.setProductId(product.getId());

        invoiceItem2.setId(invoiceItemKey2);
        invoiceItem2.setNumberOfItems(5);




    }



    @Test
    public void givenAllProperties_whenMapSetWithTwoInvoiceItemDtoToEntity_thenGetListWithSizeTwoAndAllProperties() {
        Set<InvoiceItem> invoiceItemSet = new LinkedHashSet<>();
        invoiceItemSet.add(invoiceItem);
        invoiceItemSet.add(invoiceItem2);

        Set<InvoiceItemDto> invoiceItemDtoSet = invoiceItemMapper.entityToDto(invoiceItemSet);
        assertEquals(2, invoiceItemDtoSet.size());
        InvoiceItemDto item = (InvoiceItemDto)invoiceItemDtoSet.toArray()[0];
        assertAll(
            () -> assertEquals(invoiceItem.getId(), item.getId()),
            () -> assertEquals(invoiceItem.getInvoice(), item.getInvoice()),
            () -> assertEquals(invoiceItem.getProduct(), item.getProduct()),
            () -> assertEquals(invoiceItem.getNumberOfItems(), item.getNumberOfItems())
        );
    }

    @Test
    public void givenAllProperties_whenMapInvoiceItemDtoToEntity_thenEntityHasAllProperties() {
        InvoiceItemDto invoiceItemDto = invoiceItemMapper.entityToDto(invoiceItem);
        assertAll(
            () -> assertEquals(invoiceItem.getId(), invoiceItemDto.getId()),
            () -> assertEquals(invoiceItem.getInvoice(), invoiceItemDto.getInvoice()),
            () -> assertEquals(invoiceItem.getProduct(), invoiceItemDto.getProduct()),
            () -> assertEquals(invoiceItem.getNumberOfItems(), invoiceItemDto.getNumberOfItems())
        );
    }



}
