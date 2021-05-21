package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest implements TestData {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TaxRateRepository taxRateRepository;

    @Test
    public void givenNothing_whenSaveProduct_thenFindListWithOneElementAndFindProductById(){
        Product product = new Product();

        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        Category testCategory = new Category();
        testCategory.setName(TEST_CATEGORY_NAME);
        Category newCategory = categoryRepository.save(testCategory);

        TaxRate taxRate = new TaxRate();
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);
        TaxRate newTaxRate = taxRateRepository.save(taxRate);

        product.setCategory(newCategory);
        product.setTaxRate(newTaxRate);

        System.out.println(product);

        productRepository.save(product);

        assertAll(
            () -> assertEquals(1, productRepository.findAll().size()),
            () -> assertNotNull(productRepository.findById(product.getId()))
        );

    }

}
