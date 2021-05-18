package at.ac.tuwien.sepm.groupphase.backend.basetest;

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
