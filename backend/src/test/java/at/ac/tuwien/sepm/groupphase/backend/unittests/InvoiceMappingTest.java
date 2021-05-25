package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InvoiceMappingTest implements TestData {
    private final Invoice invoice = new Invoice();
    @Autowired
    private InvoiceMapper invoiceMapping;

    @Test
    public void givenNothing_whenMapSimpleInvoiceDtoToEntity_thenEntityHasAllProperties() {
        SimpleInvoiceDto invoiceDto = invoiceMapping.invoiceToDetailedInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, invoiceDto.getId()),
            () -> assertEquals(TEST_INVOICE_DATE, invoiceDto.getDate()),
            () -> assertEquals(TEST_INVOICE_AMOUNT, invoiceDto.getAmount())
        );
    }


    @Test
    public void givenNothing_whenMapListWithTwoSimpleInvoiceDtoToDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        invoiceList.add(invoice);

        List<SimpleInvoiceDto> simpleInvoiceDtos = invoiceMapping.invoiceToSimpleInvoiceDto(invoiceList);
        assertEquals(2, simpleInvoiceDtos.size());
        SimpleInvoiceDto simpleInvoiceDto = simpleInvoiceDtos.get(0);
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, simpleInvoiceDto.getId()),
            () -> assertEquals(TEST_INVOICE_DATE, simpleInvoiceDto.getDate()),
            () -> assertEquals(TEST_INVOICE_AMOUNT, simpleInvoiceDto.getAmount())
        );
    }



}
