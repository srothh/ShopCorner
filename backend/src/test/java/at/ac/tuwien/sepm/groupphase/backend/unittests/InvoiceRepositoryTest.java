package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class InvoiceRepositoryTest implements TestData {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void givenNothing_whenSaveInvoice_thenFindListWithOneInvoiceAndFindElementById() {
        Invoice invoice = new Invoice();
        invoiceRepository.save(invoice);
        assertAll(
            () -> assertEquals(1, invoiceRepository.findAll().size()),
            () -> assertNotNull(invoiceRepository.findById(invoice.getId())),
            () -> assertNotNull(invoiceRepository.findByDate(invoice.getDate()))
        );
    }
}
