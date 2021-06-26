package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest implements TestData {

    @Mock
    Product product;
    @Spy
    @InjectMocks
    ProductService productService;
    /*@Mock
    Category category;
    @Mock
    List<Product> products;*/

    @Test
    public void createNewProduct_thenReturnProduct() {
        doReturn(product).when(productService).createProduct(product);
    }

  /*  @Test
    public void getByCategory_thenReturnProductsWithCategory() {
        product.setCategory(category);
        products.add(product);
        productService.createProduct(product);
        doReturn(products).when(productService).getAllProductsByCategory(category.getId());
    }*/
}
