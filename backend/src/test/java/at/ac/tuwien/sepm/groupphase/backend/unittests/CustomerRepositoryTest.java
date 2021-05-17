package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
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
public class CustomerRepositoryTest implements TestData {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Test
    public void givenNothing_whenSaveCustomer_thenFindListWithOneCustomerAndFindElementById() {
        Address address = new Address(0L, TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
        address = addressRepository.save(address);
        Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "");
        customerRepository.save(customer);
        assertAll(
            () -> assertEquals(1, customerRepository.findAll().size()),
            () -> assertNotNull(customerRepository.findById(customer.getId()))
        );
    }
}
