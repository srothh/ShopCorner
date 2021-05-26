package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductMappingTest implements TestData {
    private  final Product product = new Product();
    private  final Category category = new Category();
    private  final TaxRate taxRate = new TaxRate();

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void givenNothing_whenMapProductDtoToEntity_thenEntityHasAllProperties(){
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        Category category = new Category();
        category.setName(TEST_CATEGORY_NAME);

        TaxRate taxRate = new TaxRate();
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);

        product.setTaxRate(taxRate);
        product.setCategory(category);
        ProductDto productDto = this.productMapper.entityToDto(product);

        assertAll(
            () -> assertEquals(TEST_PRODUCT_ID, productDto.getId()),
            () -> assertEquals(TEST_PRODUCT_NAME, productDto.getName()),
            () -> assertEquals(TEST_PRODUCT_PRICE, productDto.getPrice()),
            () -> assertEquals(TEST_PRODUCT_DESCRIPTION, productDto.getDescription()),
            () -> assertEquals(taxRate, productDto.getTaxRate()),
            () -> assertEquals(category, productDto.getCategory())
        );

    }
    @Test
    public void giveNothing_whenMapSimpleProductDtoToEntity_thenEntityHasAllProperties(){
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);
        TaxRate taxRate = new TaxRate();
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);
        product.setTaxRate(taxRate);

        SimpleProductDto productDto = this.productMapper.simpleProductEntityToDto(product);
        assertAll(
            () -> assertEquals(TEST_PRODUCT_ID, productDto.getId()),
            () -> assertEquals(TEST_PRODUCT_NAME, productDto.getName()),
            () -> assertEquals(TEST_PRODUCT_PRICE, productDto.getPrice()),
            () -> assertEquals(TEST_PRODUCT_DESCRIPTION, productDto.getDescription())
        );

    }

    //testing porductMapper's .map function to convert base64 string to a binary format
    @Test
    public void givenNothing_whenDecodeBLOBToBase64_thenExpectValidOriginalFile(){
        String mockFile = "mockPictureFile.png";
        String encodedString = Base64.getEncoder().encodeToString(mockFile.getBytes());
        byte[] binaryContent = productMapper.map(encodedString);
        String decodedString = new String(binaryContent);
        assertEquals(mockFile,decodedString);
    }
    @Test
    //testing productMapper's .map function to convert binary data (blob) to a valid base64 string
    public void givenNothing_whenEncodeBase64ToBLOB_thenExpectValidOriginalFile(){
        String mockFile = "mockPictureFile.png";
        byte[] binaryContent = mockFile.getBytes();
        String base64String = productMapper.map(binaryContent);
        byte[] decodedByte = Base64.getDecoder().decode(base64String);
        String decodedString = new String(decodedByte);
        assertEquals(mockFile,decodedString);
    }


}
