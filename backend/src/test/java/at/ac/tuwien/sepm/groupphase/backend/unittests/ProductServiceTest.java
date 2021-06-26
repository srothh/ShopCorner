package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.ProductServiceImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceTest implements TestData {

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();
    @Mock
    Product productMock;
    @Spy
    @InjectMocks
    ProductServiceImpl productServiceMock;
    @Mock
    Category category;
    @Mock
    List<Product> products;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TaxRateRepository taxRateRepository;
    @Autowired
    private ProductService productService;

    @Test
    public void createNewProduct_thenReturnProduct() {
        doReturn(productMock).when(productServiceMock).createProduct(productMock);
    }

    @Test
    public void whenGivenOneProduct_getByCategory_thenReturnProductsWithCategory() {
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

        productService.createProduct(product);

        List<Product> products = productService.getAllProductsByCategory(newCategory.getId());
        assertAll(
            () -> assertEquals(1, products.size()),
            () -> assertEquals(product, products.get(0))
        );
    }

    @Test
    public void whenGetAllProductsWithCategoryWhenNoProductPersisted_thenThrowNullPointerException() {
        doReturn(products).when(productServiceMock).getAllProductsByCategory(0L);
    }
}
