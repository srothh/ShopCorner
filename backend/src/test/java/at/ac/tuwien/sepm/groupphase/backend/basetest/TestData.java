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

    //TEST PROPERTIES FOR PRODUCTS, TAX-RATES AND CATEGORIES
    String PRODUCTS_BASE_URI = "/api/v1/products";
    Long TEST_PRODUCT_ID = 0L;
    String TEST_PRODUCT_NAME = "Product 1";
    Double TEST_PRODUCT_PRICE = 1.20;
    String TEST_PRODUCT_DESCRIPTION = "Description1";
    Double TEST_TAX_RATE_PERCENTAGE = 20.0;
    String TEST_CATEGORY_NAME = "Cat1";
    Long TEST_CATEGORY_ID = 0L;
    String CATEGORY_BASE_URI = "/api/v1/categories";



    String OPERATOR_BASE_URI = BASE_URI + "/operators/register";

    String TEST_OPERATOR_NAME = "operator";
    String TEST_OPERATOR_LOGINNAME = "operatorLoginName";
    String TEST_OPERATOR_PASSWORD = "operatorPassword";
    String TEST_OPERATOR_EMAIL = "operator@email.com";
    Permissions TEST_OPERATOR_PERMISSION = Permissions.admin;


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
