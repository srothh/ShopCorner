package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest implements TestData {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void givenTwoOrders_whenGet_thenVerifyCustomerOrders(){
        //First Address for customer
        Address address = new Address(TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
        addressRepository.save(address);

        //Then Customer
        Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");
        Customer newCustomer = customerRepository.save(customer);

        //2 Invoices
        Invoice invoice = new Invoice();
        invoice.setInvoiceType(InvoiceType.customer);
        invoice.setDate(LocalDateTime.now());
        invoice.setInvoiceNumber("123");
        invoice.setAmount(50.0);
        invoice.setCustomerId(newCustomer.getId());
        invoiceRepository.save(invoice);

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceType(InvoiceType.customer);
        invoice2.setDate(LocalDateTime.now());
        invoice2.setInvoiceNumber("456");
        invoice2.setAmount(150.0);
        invoice2.setCustomerId(newCustomer.getId());
        invoiceRepository.save(invoice2);

        //2 Orders
        Order order1 = new Order(invoice, newCustomer);
        Order order2 = new Order(invoice2, newCustomer);
        orderRepository.save(order1);
        orderRepository.save(order2);


        assertAll(
            () -> assertEquals(2, orderRepository.findAll().size()),
            () -> assertNotNull(orderRepository.findAllByCustomerId(PageRequest.of(0,5),newCustomer.getId())),
            () -> assertEquals(2,orderRepository.findAllByCustomerId(PageRequest.of(0,5),newCustomer.getId()).getContent().size())
        );

    }

}
