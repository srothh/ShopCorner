package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";

    //TEST PROPERTIES FOR Operator Overview
    String OPERATORS_BASE_URI = "/api/v1/operators";

    String TEST_ADMIN_NAME = "Admin";
    String TEST_ADMIN_LOGINNAME = "TestAdmin";
    String TEST_ADMIN_PASSWORD = "TestPassword";
    String TEST_ADMIN_EMAIL = "admin@gmail.com";
    Permissions TEST_ADMIN_PERMISSIONS = Permissions.admin;

    String TEST_EMPLOYEE_NAME = "Employee";
    String TEST_EMPLOYEE_LOGINNAME = "TestEmployee";
    String TEST_EMPLOYEE_PASSWORD = "TestPassword";
    String TEST_EMPLOYEE_EMAIL = "employee@gmail.com";
    Permissions TEST_EMPLOYEE_PERMISSIONS = Permissions.employee;

    String ADMIN_USER = "admin@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
        }
    };
    String DEFAULT_USER = "admin@email.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
        }
    };

}
