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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
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
    public void givenOneAdmin_whenFindAllWithPageAndPermissionAdminAsEmployee_thenReturnForbidden()
        throws Exception {
        operatorRepository.save(admin);

        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "?page=0&page_count=0&permissions=admin")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_USER, EMPLOYEE_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void givenTwoOperators_whenGetCount_thenArrayWithTwoOnes()
        throws Exception {
        operatorRepository.save(admin);
        operatorRepository.save(employee);

        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "/count")
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

    @Test
    public void givenOneOperator_whenDelete_findAllAfterDeleteReturnsEmptyList()
        throws Exception {
        operatorRepository.save(admin);

        MvcResult mvcResultDelete = this.mockMvc.perform(delete(OPERATORS_BASE_URI + "/" + admin.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseDelete = mvcResultDelete.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), responseDelete.getStatus());

        MvcResult mvcResultGet = this.mockMvc.perform(get(OPERATORS_BASE_URI + "?page=0&page_count=0&permissions=admin")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseGet = mvcResultGet.getResponse();

        assertEquals(HttpStatus.OK.value(), responseGet.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, responseGet.getContentType());

        List<OverviewOperatorDto> overviewOperatorDtos = Arrays.asList(objectMapper.readValue(responseGet.getContentAsString(),
            OverviewOperatorDto[].class));

        assertEquals(0, overviewOperatorDtos.size());
    }

    @Test
    public void givenNothing_whenDelete_thenNotFoundResponse()
        throws Exception {
        MvcResult mvcResultDelete = this.mockMvc.perform(delete(OPERATORS_BASE_URI + "/" + 100)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseDelete = mvcResultDelete.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), responseDelete.getStatus());
    }

    @Test
    public void givenOneOperator_whenPutByNonExistingId_then404() throws Exception {
        operatorRepository.save(operator);

        employee.setId(-1L);
        OperatorDto operatorDto = operatorMapper.entityToDto(employee);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(put(OPERATOR_BASE_URI + "/" + employee.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_EMPLOYEE_LOGINNAME, EMPLOYEE_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus())
        );
    }

    @Test
    public void givenOneOperator_whenPutByExistingId_thenVerifyOperatorChanged() throws Exception {
        Operator updatedOperator = operatorRepository.save(operator);
        updatedOperator.setName("Updated Name");
        updatedOperator.setPassword("unchanged");
        updatedOperator.setEmail("updated@email.com");

        OperatorDto operatorDto = operatorMapper.entityToDto(updatedOperator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(put(OPERATOR_BASE_URI + "/" + updatedOperator.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_OPERATOR_LOGINNAME, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        OperatorDto operatorResponse = objectMapper.readValue(response.getContentAsString(),
            OperatorDto.class);

        assertNotNull(operatorResponse.getId());
        assertNotNull(operatorResponse.getName());
        assertNotNull(operatorResponse.getLoginName());
        assertNotNull(operatorResponse.getEmail());
        assertNotNull(operatorResponse.getPermissions());
        operatorResponse.setPassword(null);
        operator.setPassword(null);

        assertAll(
            () -> assertEquals(updatedOperator.getId(), operatorMapper.dtoToEntity(operatorResponse).getId()),
            () -> assertEquals(updatedOperator.getEmail(), operatorMapper.dtoToEntity(operatorResponse).getEmail()),
            () -> assertEquals(updatedOperator.getName(), operatorMapper.dtoToEntity(operatorResponse).getName()),
            () -> assertEquals(operator.getLoginName(), operatorMapper.dtoToEntity(operatorResponse).getLoginName()),
            () -> assertEquals(operator.getPermissions(), operatorMapper.dtoToEntity(operatorResponse).getPermissions())
        );
    }

    @Test
    public void givenTwoOperators_whenPutByExistingIdAndChangedOperatorAlreadyExists_then422() throws Exception {
        Operator firstOperator = operatorRepository.save(admin);
        Operator updatedOperator = operatorRepository.save(operator);

        updatedOperator.setLoginName(firstOperator.getLoginName());
        updatedOperator.setEmail(firstOperator.getEmail());
        updatedOperator.setPassword("unchanged");

        OperatorDto operatorDto = operatorMapper.entityToDto(updatedOperator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(put(OPERATOR_BASE_URI + "/" + updatedOperator.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_OPERATOR_LOGINNAME, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus())
        );
    }

    @Test
    public void givenTwoOperators_whenPutByExistingIdAndIllegalAccess_then403() throws Exception {
        operatorRepository.save(admin);
        Operator updatedOperator = operatorRepository.save(operator);

        updatedOperator.setName("New Name");
        updatedOperator.setPassword("unchanged");

        OperatorDto operatorDto = operatorMapper.entityToDto(updatedOperator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(put(OPERATOR_BASE_URI + "/" + updatedOperator.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_ADMIN_LOGINNAME, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus())
        );
    }


    @Test
    public void givenOneOperator_whenPutInvalid_then400() throws Exception {
        Operator updatedOperator = operatorRepository.save(operator);
        updatedOperator.setName(null);
        updatedOperator.setLoginName(null);
        updatedOperator.setEmail(null);
        updatedOperator.setPassword("unchanged");

        OperatorDto operatorDto = operatorMapper.entityToDto(updatedOperator);
        String body = objectMapper.writeValueAsString(operatorDto);

        MvcResult mvcResult = this.mockMvc.perform(put(OPERATOR_BASE_URI + "/" + updatedOperator.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_OPERATOR_LOGINNAME, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus())
        );
    }

    @Test
    public void givenNothing_whenFindByLoginName_then404() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "/" + operator.getLoginName())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TEST_OPERATOR_LOGINNAME, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus())
        );
    }

    @Test
    public void givenTwoOperators_whenFindByLoginName_thenCorrectOperator() throws Exception {
        Operator operator = operatorRepository.save(admin);
        operatorRepository.save(employee);

        MvcResult mvcResult = this.mockMvc.perform(get(OPERATORS_BASE_URI + "/" + admin.getLoginName())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        OperatorDto operatorResponse = objectMapper.readValue(response.getContentAsString(),
            OperatorDto.class);

        assertAll(
            () -> assertEquals(operator.getId(), operatorMapper.dtoToEntity(operatorResponse).getId()),
            () -> assertEquals(operator.getEmail(), operatorMapper.dtoToEntity(operatorResponse).getEmail()),
            () -> assertEquals(operator.getName(), operatorMapper.dtoToEntity(operatorResponse).getName()),
            () -> assertEquals(operator.getLoginName(), operatorMapper.dtoToEntity(operatorResponse).getLoginName()),
            () -> assertEquals(operator.getPermissions(), operatorMapper.dtoToEntity(operatorResponse).getPermissions())
        );
    }


}

