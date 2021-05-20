package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
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
public class OperatorMappingTest implements TestData {

    private final Operator admin = new Operator(0L, TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
    private final Operator employee = new Operator(1L, TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);

    @Autowired
    private OperatorMapper operatorMapper;

    @Test
    public void givenNothing_whenMapEntityToOverviewOperatorDto_thenDtoHasAllPropertiesExceptPassword() {
        OverviewOperatorDto overviewOperatorDto = operatorMapper.operatorToOverviewOperatorDto(admin);
        assertAll(
            () -> assertEquals(0L, overviewOperatorDto.getId()),
            () -> assertEquals(TEST_ADMIN_NAME, overviewOperatorDto.getName()),
            () -> assertEquals(TEST_ADMIN_LOGINNAME, overviewOperatorDto.getLoginName()),
            () -> assertEquals(TEST_ADMIN_EMAIL, overviewOperatorDto.getEmail()),
            () -> assertEquals(TEST_ADMIN_PERMISSIONS, overviewOperatorDto.getPermissions())
        );
    }

    @Test
    public void givenNothing_whenMapListWithTwoOperatorEntitiesToOverviewOperatorDto_thenGetListWithSizeTwoAndAllPropertiesExceptPassword() {
        List<Operator> operators = new ArrayList<>();
        operators.add(admin);
        operators.add(employee);

        List<OverviewOperatorDto> overviewOperatorDtos = operatorMapper.operatorToOverviewOperatorDto(operators);
        assertEquals(2, overviewOperatorDtos.size());
        OverviewOperatorDto adminDto = overviewOperatorDtos.get(0);
        assertAll(
            () -> assertEquals(0L, adminDto.getId()),
            () -> assertEquals(TEST_ADMIN_NAME, adminDto.getName()),
            () -> assertEquals(TEST_ADMIN_LOGINNAME, adminDto.getLoginName()),
            () -> assertEquals(TEST_ADMIN_EMAIL, adminDto.getEmail()),
            () -> assertEquals(TEST_ADMIN_PERMISSIONS, adminDto.getPermissions())
        );
        OverviewOperatorDto employeeDto = overviewOperatorDtos.get(1);
        assertAll(
            () -> assertEquals(1L, employeeDto.getId()),
            () -> assertEquals(TEST_EMPLOYEE_NAME, employeeDto.getName()),
            () -> assertEquals(TEST_EMPLOYEE_LOGINNAME, employeeDto.getLoginName()),
            () -> assertEquals(TEST_EMPLOYEE_EMAIL, employeeDto.getEmail()),
            () -> assertEquals(TEST_EMPLOYEE_PERMISSIONS, employeeDto.getPermissions())
        );
    }
}
