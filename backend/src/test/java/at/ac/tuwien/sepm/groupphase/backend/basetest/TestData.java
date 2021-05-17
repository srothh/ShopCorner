package at.ac.tuwien.sepm.groupphase.backend.basetest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    String TEST_CUSTOMER_LOGINNAME = "test";
    String TEST_CUSTOMER_NAME = "Bonus Eventus";
    String TEST_CUSTOMER_PASSWORD = "strongpassword";
    String TEST_CUSTOMER_EMAIL = "test@gmail.com";
    String TEST_ADDRESS_STREET = "testStreet";
    int TEST_ADDRESS_POSTALCODE = 1000;
    String TEST_ADDRESS_HOUSENUMBER = "1a";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String ADDRESS_BASE_URI = BASE_URI + "/address";
    String CUSTOMER_BASE_URI = BASE_URI + "/customers";

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
