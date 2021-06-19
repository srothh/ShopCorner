package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MeEndpointTest implements TestData{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private CustomerMapper customerMapper;

    private final Address address = new Address(TEST_ADDRESS_STREET, TEST_ADDRESS_POSTALCODE, TEST_ADDRESS_HOUSENUMBER, 0, "0");
    private final Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");
    private final Customer customer2 = new Customer("mail@gmail.com", TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, "login", address, 0L, "");

    @BeforeEach
    @CacheEvict(value = "counts",allEntries = true)
    public void beforeEach() {
        customerRepository.deleteAll();
        Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");
        Customer customer2 = new Customer("mail@gmail.com", TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, "login", address, 0L, "");
    }


    @Test
    public void givenNothing_whenPut_then404() throws Exception {

        customer.setId(1L);
        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerRegistrationDto(customer);
        String body = objectMapper.writeValueAsString(customerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(ME_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_CUSTOMER_LOGINNAME, CUSTOMER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus())
        );
    }

    @Test
    public void givenOneCustomer_whenPutByExistingId_thenVerifyCustomerChanged() throws Exception {
        addressRepository.save(address);
        Customer updatedCustomer =  customerRepository.save(customer);

        updatedCustomer.setName("New Name");
        updatedCustomer.setPassword("unchanged");

        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerRegistrationDto(updatedCustomer);
        String body = objectMapper.writeValueAsString(customerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(ME_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_CUSTOMER_LOGINNAME, CUSTOMER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CustomerRegistrationDto customerResponse = objectMapper.readValue(response.getContentAsString(),
            CustomerRegistrationDto.class);

        assertNotNull(customerResponse.getId());
        assertNotNull(customerResponse.getName());
        assertNotNull(customerResponse.getLoginName());
        assertNotNull(customerResponse.getEmail());
        assertNotNull(customerResponse.getAddress());
        customerResponse.setPassword(null);

        assertAll(
            () -> assertEquals(updatedCustomer.getId(), customerMapper.customerDtoToCustomer(customerResponse).getId()),
            () -> assertEquals(updatedCustomer.getEmail(), customerMapper.customerDtoToCustomer(customerResponse).getEmail()),
            () -> assertEquals(updatedCustomer.getName(), customerMapper.customerDtoToCustomer(customerResponse).getName()),
            () -> assertEquals(updatedCustomer.getLoginName(), customerMapper.customerDtoToCustomer(customerResponse).getLoginName()),
            () -> assertEquals(updatedCustomer.getAddress(), customerMapper.customerDtoToCustomer(customerResponse).getAddress()),
            () -> assertEquals(updatedCustomer.getPhoneNumber(), customerMapper.customerDtoToCustomer(customerResponse).getPhoneNumber())
        );
    }

    @Test
    public void givenTwoCustomers_whenPutByExistingIdAndIllegalAccess_then403() throws Exception {
        addressRepository.save(address);
        customerRepository.save(customer);
        Customer updatedCustomer = customerRepository.save(customer2);

        updatedCustomer.setName("New Name");
        updatedCustomer.setPassword("unchanged");

        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerRegistrationDto(updatedCustomer);
        String body = objectMapper.writeValueAsString(customerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(ME_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_CUSTOMER_LOGINNAME, CUSTOMER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus())
        );
    }

    @Test
    public void givenTwoCustomers_whenPutByExistingIdAndChangedCustomerAlreadyExists_then422() throws Exception {
        addressRepository.save(address);
        Customer firstCustomer = customerRepository.save(customer);
        Customer updatedCustomer = customerRepository.save(customer2);

        updatedCustomer.setLoginName(firstCustomer.getLoginName());
        updatedCustomer.setEmail(firstCustomer.getEmail());
        updatedCustomer.setPassword("unchanged");

        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerRegistrationDto(updatedCustomer);
        String body = objectMapper.writeValueAsString(customerDto);

        MvcResult mvcResult = this.mockMvc.perform(put(ME_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(customer2.getLoginName(), CUSTOMER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus())
        );
    }

}
