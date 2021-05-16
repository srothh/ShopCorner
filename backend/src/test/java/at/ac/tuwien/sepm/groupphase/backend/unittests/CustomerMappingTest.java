package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
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
public class CustomerMappingTest implements TestData {
    private final Address address = new Address(0L, TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
    private final Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "");
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void givenNothing_whenMapCustomerToCustomerRegistrationDto_thenEntityHasAllProperties() {
        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerDto(customer);
        customerDto.getAddress().setId(0L);
        assertAll(
            () -> assertEquals(0, customerDto.getId()),
            () -> assertEquals(TEST_CUSTOMER_EMAIL, customerDto.getEmail()),
            () -> assertEquals(TEST_CUSTOMER_PASSWORD, customerDto.getPassword()),
            () -> assertEquals(TEST_CUSTOMER_NAME, customerDto.getName()),
            () -> assertEquals(TEST_CUSTOMER_LOGINNAME, customerDto.getLoginName()),
            () -> assertEquals(address, addressMapper.addressDtoToAddress(customerDto.getAddress()))
        );
    }

    @Test
    public void givenNothing_whenMapListWithTwoAddressEntitiesToDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer);
        List<CustomerRegistrationDto> customerDtos = customerMapper.customerListToCustomerDtoList(customers);
        assertEquals(2, customerDtos.size());
        CustomerRegistrationDto customerDto = customerDtos.get(0);
        customerDto.getAddress().setId(0L);
        assertAll(
            () -> assertEquals(0, customerDto.getId()),
            () -> assertEquals(TEST_CUSTOMER_EMAIL, customerDto.getEmail()),
            () -> assertEquals(TEST_CUSTOMER_PASSWORD, customerDto.getPassword()),
            () -> assertEquals(TEST_CUSTOMER_NAME, customerDto.getName()),
            () -> assertEquals(TEST_CUSTOMER_LOGINNAME, customerDto.getLoginName()),
            () -> assertEquals(address, addressMapper.addressDtoToAddress(customerDto.getAddress()))
        );
    }

}
