package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import at.ac.tuwien.sepm.groupphase.backend.util.OperatorSpecifications;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class OperatorRepositoryTest implements TestData {

    private final Operator admin = new Operator(TEST_ADMIN_NAME, TEST_ADMIN_LOGINNAME, TEST_ADMIN_PASSWORD, TEST_ADMIN_EMAIL, TEST_ADMIN_PERMISSIONS);
    private final Operator employee = new Operator(TEST_EMPLOYEE_NAME, TEST_EMPLOYEE_LOGINNAME, TEST_EMPLOYEE_PASSWORD, TEST_EMPLOYEE_EMAIL, TEST_EMPLOYEE_PERMISSIONS);

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

    @Test
    public void givenNothing_whenSaveTwoOperators_thenFindOperatorsWithPageAndPermissionWithOneElementEachAndFindOperatorById() {
        operatorRepository.save(admin);
        operatorRepository.save(employee);
        Pageable returnPage = PageRequest.of(0, 15);

        assertAll(
            () -> assertNotNull(operatorRepository.findById(admin.getId())),
            () -> assertNotNull(operatorRepository.findById(employee.getId())),
            () -> assertEquals(1, operatorRepository.findAll(OperatorSpecifications.hasPermission(Permissions.admin), returnPage).getContent().size()),
            () -> assertEquals(1, operatorRepository.findAll(OperatorSpecifications.hasPermission(Permissions.employee), returnPage).getContent().size())
        );
    }

    @Test
    public void givenNothing_whenSaveOperator_thenFindOperatorWithOneElementAndFindOperatorById() {
        Operator operator = new Operator(TEST_OPERATOR_NAME, TEST_OPERATOR_LOGINNAME, TEST_OPERATOR_PASSWORD, TEST_OPERATOR_EMAIL, TEST_OPERATOR_PERMISSION);

        operatorRepository.save(operator);

        assertAll(
            () -> assertEquals(1, operatorRepository.findAll().size()),
            () -> assertNotNull(operatorRepository.findById(operator.getId()))
        );
    }

    @Test
    public void givenOneOperator_whenDeleteByIdOperator_thenFindAllEmpty() {
        operatorRepository.save(admin);

        operatorRepository.deleteById(admin.getId());

        assertAll(
            () -> assertEquals(0,operatorRepository.findAll().size())
        );
    }

    @Test
    public void givenOneOperator_whenSetPermissionById_thenFindAllAdminsReturnsListWithSizeOne() {
        operatorRepository.save(employee);
        Pageable returnPage = PageRequest.of(0, 15);

        operatorRepository.setOperatorPermissionsById(Permissions.admin, employee.getId());

        assertEquals(1, operatorRepository.findAll(OperatorSpecifications.hasPermission(Permissions.admin), returnPage).getContent().size());
    }
}