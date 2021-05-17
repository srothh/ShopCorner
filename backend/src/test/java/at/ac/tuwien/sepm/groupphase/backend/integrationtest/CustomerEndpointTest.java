package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

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
    private final Customer customer2 = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "");
    @BeforeEach
    public void beforeEach(){
        customerRepository.deleteAll();
        Customer customer = new Customer(TEST_CUSTOMER_EMAIL, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_NAME, TEST_CUSTOMER_LOGINNAME, address, 0L, "1");
    }

    @Test
    public void givenNothing_whenPost_thenCustomerWithAllSetPropertiesPlusId() throws Exception {
        CustomerRegistrationDto customerDto = customerMapper.customerToCustomerDto(customer);
        String body = objectMapper.writeValueAsString(customerDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CustomerRegistrationDto customerResponse = objectMapper.readValue(response.getContentAsString(),
            CustomerRegistrationDto.class);

        assertNotNull(customerResponse.getId());
        assertNotNull(customerResponse.getName());
        assertNotNull(customerResponse.getLoginName());
        assertNotNull(customerResponse.getEmail());
        assertNotNull(customerResponse.getAddress());
        customerResponse.setId(null);
        customerResponse.setPassword(null);
        customerResponse.getAddress().setId(null);
        customer.setPassword(null);

        assertAll(
            () -> assertEquals(customer.getEmail(), customerMapper.customerDtoToCustomer(customerResponse).getEmail()),
            () -> assertEquals(customer.getName(), customerMapper.customerDtoToCustomer(customerResponse).getName()),
            () -> assertEquals(customer.getLoginName(), customerMapper.customerDtoToCustomer(customerResponse).getLoginName()),
            () -> assertEquals(customer.getAddress(), customerMapper.customerDtoToCustomer(customerResponse).getAddress())
            //() -> assertEquals(customer.getPhoneNumber(), customerMapper.customerDtoToCustomer(customerResponse).getPhoneNumber())
        );
    }

    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        customer.setName(null);
        customer.setEmail(null);
        customer.setLoginName(null);
        CustomerRegistrationDto dto = customerMapper.customerToCustomerDto(customer);
        String body = objectMapper.writeValueAsString(dto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(5, errors.length);
            }
        );
    }
    @Test
    public void givenNothing_whenAddressInvalid_then404() throws Exception {
        customer.setAddress(null);
        CustomerRegistrationDto dto = customerMapper.customerToCustomerDto(customer);
        String body = objectMapper.writeValueAsString(dto);

        MvcResult mvcResult = this.mockMvc.perform(post(CUSTOMER_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus())
        );
    }

}
