package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
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
            () -> assertEquals(0, invoiceDto.getId()),
            () -> assertEquals(TEST_ADDRESS_STREET, invoiceDto.getDate()),
            () -> assertEquals(TEST_ADDRESS_POSTALCODE, invoiceDto.getAmount())
        );
    }

    @Test
    public void givenNothing_whenMapDetailedInvoiceDtoToEntity_thenEntityHasAllProperties() {
        DetailedInvoiceDto invoiceDto = invoiceMapping.invoiceToDetailedInvoiceDto(invoice);
        assertAll(
            () -> assertEquals(0, invoiceDto.getId()),
            () -> assertEquals(TEST_ADDRESS_STREET, invoiceDto.getDate()),
            () -> assertEquals(TEST_ADDRESS_POSTALCODE, invoiceDto.getAmount()),
            () -> assertEquals(TEST_ADDRESS_HOUSENUMBER, invoiceDto.getItems())
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
            () -> assertEquals(0, simpleInvoiceDto.getId()),
            () -> assertEquals(TEST_ADDRESS_STREET, simpleInvoiceDto.getDate()),
            () -> assertEquals(TEST_ADDRESS_POSTALCODE, simpleInvoiceDto.getAmount())
        );
    }



}
