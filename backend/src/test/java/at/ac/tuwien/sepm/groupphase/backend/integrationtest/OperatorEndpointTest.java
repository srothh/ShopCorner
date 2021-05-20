package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc

public class OperatorEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private OperatorMapper operatorMapper;


    private final Operator admin = new Operator(1L, TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
    private final Operator employee = new Operator(2L, TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);

    @BeforeEach
    public void beforeEach() {
        operatorRepository.deleteAll();
        Operator admin = new Operator(1L, TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
        Operator employee = new Operator(2L, TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);
    }

    @Test
    public void givenNothing_whenFindAll_thenEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<OverviewOperatorDto> overviewOperatorDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            OverviewOperatorDto[].class));

        assertEquals(0, overviewOperatorDtos.size());
    }

    @Test
    public void givenTwoOperators_whenFindAll_thenListWithSizeTwoAndOverviewOperatorsWithAllPropertiesExceptPassword()
        throws Exception {
        operatorRepository.save(admin);
        operatorRepository.save(employee);

        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<OverviewOperatorDto> overviewOperatorDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            OverviewOperatorDto[].class));

        assertEquals(2, overviewOperatorDtos.size());
        OverviewOperatorDto adminDto = overviewOperatorDtos.get(0);
        assertAll(
            () -> assertEquals(1L, adminDto.getId()),
            () -> assertEquals(TEST_ADMIN_NAME, adminDto.getName()),
            () -> assertEquals(TEST_ADMIN_LOGINNAME, adminDto.getLoginName()),
            () -> assertEquals(TEST_ADMIN_EMAIL, adminDto.getEmail()),
            () -> assertEquals(TEST_ADMIN_PERMISSIONS.name(), adminDto.getPermissions())
        );
        OverviewOperatorDto employeeDto = overviewOperatorDtos.get(1);
        assertAll(
            () -> assertEquals(2L, employeeDto.getId()),
            () -> assertEquals(TEST_EMPLOYEE_NAME, employeeDto.getName()),
            () -> assertEquals(TEST_EMPLOYEE_LOGINNAME, employeeDto.getLoginName()),
            () -> assertEquals(TEST_EMPLOYEE_EMAIL, employeeDto.getEmail()),
            () -> assertEquals(TEST_EMPLOYEE_PERMISSIONS.name(), employeeDto.getPermissions())
        );
    }

}