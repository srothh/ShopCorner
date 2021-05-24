package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    private final Operator admin = new Operator(TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
    private final Operator employee = new Operator(TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);
    private final Operator operator = new Operator(TEST_OPERATOR_NAME, TEST_OPERATOR_LOGINNAME, TEST_OPERATOR_PASSWORD, TEST_OPERATOR_EMAIL, TEST_OPERATOR_PERMISSION);

    @BeforeEach
    public void beforeEach() {
        operatorRepository.deleteAll();
        Operator admin = new Operator(TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
        Operator employee = new Operator(TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);
        Operator operator = new Operator(TEST_OPERATOR_NAME, TEST_OPERATOR_LOGINNAME, TEST_OPERATOR_PASSWORD, TEST_OPERATOR_EMAIL, TEST_OPERATOR_PERMISSION);
    }

    @Test
    public void givenNothing_whenPost_thenOperatorWithAllSetPropertiesPlusId() throws Exception {
        OperatorDto operatorDto = operatorMapper.entityToDto(operator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(post(OPERATOR_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        OperatorDto operatorResponse = objectMapper.readValue(response.getContentAsString(),
            OperatorDto.class);

        assertNotNull(operatorResponse.getId());
        assertNotNull(operatorResponse.getName());
        assertNotNull(operatorResponse.getLoginName());
        assertNotNull(operatorResponse.getEmail());
        assertNotNull(operatorResponse.getPermissions());
        operatorResponse.setId(null);
        operatorResponse.setPassword(null);
        operator.setPassword(null);

        assertAll(
            () -> assertEquals(operator.getEmail(), operatorMapper.dtoToEntity(operatorResponse).getEmail()),
            () -> assertEquals(operator.getName(), operatorMapper.dtoToEntity(operatorResponse).getName()),
            () -> assertEquals(operator.getLoginName(), operatorMapper.dtoToEntity(operatorResponse).getLoginName()),
            () -> assertEquals(operator.getPermissions(), operatorMapper.dtoToEntity(operatorResponse).getPermissions())
        );
    }


    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        operator.setName(null);
        operator.setEmail(null);
        operator.setLoginName(null);
        OperatorDto operatorDto = operatorMapper.entityToDto(operator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(post(OPERATOR_BASE_URI)
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
                assertEquals(6, errors.length);
            }
        );
    }

    @Test
    public void givenOneOperator_whenPostAlreadyExists_then422() throws Exception {
        operatorRepository.save(operator);

        OperatorDto operatorDto = operatorMapper.entityToDto(operator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(post(OPERATOR_BASE_URI)
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

    @Test
    public void givenNothing_whenFindAll_thenEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "?page=0&page_count=0&permissions=admin")
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
    public void givenTwoOperators_whenFindAllWithPageAndPermission_thenListWithSizeOneAndOverviewOperatorsWithAllPropertiesExceptPassword()
        throws Exception {
        operatorRepository.save(admin);
        operatorRepository.save(employee);

        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "?page=0&page_count=0&permissions=admin")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<OverviewOperatorDto> overviewOperatorDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            OverviewOperatorDto[].class));

        assertEquals(1, overviewOperatorDtos.size());
        OverviewOperatorDto adminDto = overviewOperatorDtos.get(0);
        assertAll(
            () -> assertEquals(admin.getId(), adminDto.getId()),
            () -> assertEquals(TEST_ADMIN_NAME, adminDto.getName()),
            () -> assertEquals(TEST_ADMIN_LOGINNAME, adminDto.getLoginName()),
            () -> assertEquals(TEST_ADMIN_EMAIL, adminDto.getEmail()),
            () -> assertEquals(TEST_ADMIN_PERMISSIONS, adminDto.getPermissions())
        );
    }

    @Test
    public void givenTwoOperators_whenGetCount_thenArrayWithTwoOnes()
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

        int[] count = (objectMapper.readValue(response.getContentAsString(), int[].class));

        assertEquals(2, count.length);
        int adminCount = count[0];
        assertEquals(1, adminCount);
        int employeeCount = count[1];
        assertEquals(1, employeeCount);
    }
}

