package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class OperatorMappingTest implements TestData{

    private final Operator operator = new Operator(0L, TEST_OPERATOR_NAME, TEST_OPERATOR_LOGINNAME, TEST_OPERATOR_PASSWORD, TEST_OPERATOR_EMAIL, TEST_OPERATOR_PERMISSION);

    @Autowired
    private OperatorMapper operatorMapper;

    @Test
    public void givenNothing_whenMapOperatorDtoToEntity_thenEntityHasAllProperties() {
        OperatorDto operatorDto = operatorMapper.entityToDto(operator);
        assertAll(
            () -> assertEquals(0L, operatorDto.getId()),
            () -> assertEquals(TEST_OPERATOR_NAME, operatorDto.getName()),
            () -> assertEquals(TEST_OPERATOR_LOGINNAME, operatorDto.getLoginName()),
            () -> assertEquals(TEST_OPERATOR_PASSWORD, operatorDto.getPassword()),
            () -> assertEquals(TEST_OPERATOR_EMAIL, operatorDto.getEmail()),
            () -> assertEquals(TEST_OPERATOR_PERMISSION, operatorDto.getPermissions())
        );
    }


}
