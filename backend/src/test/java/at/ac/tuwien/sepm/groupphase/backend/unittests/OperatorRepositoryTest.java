package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class OperatorRepositoryTest implements TestData {

    private final Operator admin = new Operator(1L, TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
    private final Operator employee = new Operator(2L, TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);

    @Autowired
    private OperatorRepository operatorRepository;

    @Test
    public void givenNothing_whenSaveTwoOperators_thenFindOperatorsWithTwoElementsAndFindOperatorById() {
        operatorRepository.save(admin);
        operatorRepository.save(employee);

        assertAll(
            () -> assertNotNull(operatorRepository.findById(admin.getId())),
            () -> assertNotNull(operatorRepository.findById(employee.getId())),
            () -> assertEquals(2, operatorRepository.findAll().size())
        );
    }

    public void givenNothing_whenSaveOperator_thenFindOperatorWithOneElementAndFindOperatorById() {
        Operator operator = new Operator(TEST_OPERATOR_NAME, TEST_OPERATOR_LOGINNAME, TEST_OPERATOR_PASSWORD, TEST_OPERATOR_EMAIL, TEST_OPERATOR_PERMISSION);

        operatorRepository.save(operator);

        assertAll(
            () -> assertEquals(1, operatorRepository.findAll().size()),
            () -> assertNotNull(operatorRepository.findById(operator.getId()))
        );
    }
}