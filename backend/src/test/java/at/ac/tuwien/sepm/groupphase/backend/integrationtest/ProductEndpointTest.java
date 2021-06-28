package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private final Product product = new Product();
    private final Product product2 = new Product();
    private final Product product3 = new Product();
    private final Category category = new Category();
    private final TaxRate taxRate = new TaxRate();

    @BeforeEach
    public void beforeEach() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);

        category.setId(1L);
        category.setName(TEST_CATEGORY_NAME);

        taxRate.setId(1L);
        taxRate.setPercentage(TEST_TAX_RATE_PERCENTAGE);

    }

    @Test
    public void givenACategoryAndATaxRate_whenPost_thenProductWithAllSetPropertiesPlusId() throws Exception {
        Category newCategory = categoryRepository.save(category);
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        product.setTaxRate(newTaxRate);
        product.setCategory(newCategory);
        ProductDto productDto = productMapper.entityToDto(product);
        String body = objectMapper.writeValueAsString(productDto);

        MvcResult mvcResult = this.mockMvc.perform(
            post(PRODUCTS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        ProductDto productResponse = objectMapper.readValue(response.getContentAsString(),
            ProductDto.class);

        assertNotNull(productResponse.getId());
        assertNotNull(productResponse.getName());
        assertNotNull(productResponse.getDescription());
        assertNotNull(productResponse.getPrice());
        assertNotNull(productResponse.getTaxRate());
        assertNotNull(productResponse.getCategory());

        productResponse.setId(null);


        assertAll(
            () -> assertEquals(product.getName(), productMapper.dtoToEntity(productResponse).getName()),
            () -> assertEquals(product.getDescription(), productMapper.dtoToEntity(productResponse).getDescription()),
            () -> assertEquals(product.getPrice(), productMapper.dtoToEntity(productResponse).getPrice()),
            () -> assertEquals(product.getTaxRate(), productMapper.dtoToEntity(productResponse).getTaxRate()),
            () -> assertEquals(product.getCategory(), productMapper.dtoToEntity(productResponse).getCategory())
        );

    }

    @Test
    public void givenACategoryAndATaxRate_whenPostInvalid_then400() throws Exception {
        categoryRepository.save(category);
        taxRateRepository.save(taxRate);
        product.setName(null);
        product.setPrice(-100.0);

        ProductDto productDto = productMapper.entityToDto(product);
        String body = objectMapper.writeValueAsString(productDto);

        MvcResult mvcResult = this.mockMvc.perform(post(PRODUCTS_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(2, errors.length);
            }
        );
    }

    @Test
    public void givenATaxRate_whenPostByNonExistingId_then404() throws Exception {
        taxRateRepository.save(taxRate);
        product.setTaxRate(taxRate);
        category.setId(-1L);
        product.setCategory(category);
        ProductDto productDto = productMapper.entityToDto(product);
        String body = objectMapper.writeValueAsString(productDto);

        MvcResult mvcResult = this.mockMvc.perform(post(PRODUCTS_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void givenACategoryAndATaxRate_whenPut_thenVerifyProductChanged() throws Exception {
        Category newCategory = categoryRepository.save(category);
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        product.setCategory(newCategory);
        product.setTaxRate(newTaxRate);
        Product newProduct = productRepository.save(product);
        newProduct.setName("ChangedName");
        ProductDto productDto = productMapper.entityToDto(newProduct);
        String body = objectMapper.writeValueAsString(productDto);

        ResultActions mvcResult = this.mockMvc.perform(
            put(PRODUCTS_BASE_URI + '/' + newProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
                .content(body))
            .andExpect(status().isOk());

        MockHttpServletResponse response = mvcResult.andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        Product updatedProduct = this.productRepository.findById(productDto.getId()).orElseThrow(() -> new NotFoundException("Error"));

        assertAll(
            () -> assertEquals(newProduct.getName(), updatedProduct.getName()),
            () -> assertEquals(newProduct.getDescription(), updatedProduct.getDescription()),
            () -> assertEquals(newProduct.getPrice(), updatedProduct.getPrice()),
            () -> assertEquals(newProduct.getTaxRate(), updatedProduct.getTaxRate()),
            () -> assertEquals(newProduct.getCategory(), updatedProduct.getCategory())
        );
    }

    @Test
    public void givenATaxRate_whenPutByNonExistingId_then404() throws Exception {
        taxRateRepository.save(taxRate);
        product.setId(1000L);
        ProductDto productDto = productMapper.entityToDto(product);
        String body = objectMapper.writeValueAsString(productDto);

        ResultActions mvcResult = this.mockMvc.perform(
            put(PRODUCTS_BASE_URI + "/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
                .content(body))
            .andExpect(status().isNotFound());

    }

    @Test
    public void givenACategoryAndATaxRate_whenPutIllegalArgument_then400() throws Exception {
        Category newCategory = categoryRepository.save(category);
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        product.setCategory(newCategory);
        product.setTaxRate(newTaxRate);
        Product newProduct = productRepository.save(product);
        newProduct.setName("           ");
        ProductDto productDto = productMapper.entityToDto(newProduct);
        String body = objectMapper.writeValueAsString(productDto);

        ResultActions mvcResult = this.mockMvc.perform(
            put(PRODUCTS_BASE_URI + "/" + newProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest());

        MockHttpServletResponse response = mvcResult.andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    public void givenATaxRate_whenDelete_thenVerifyProductDeleted() throws Exception {
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        product.setTaxRate(newTaxRate);
        Product newProduct = productRepository.save(product);

        assertEquals(1, this.productRepository.findAll().size());

        ResultActions mvcResult = this.mockMvc.perform(
            delete(PRODUCTS_BASE_URI + "/" + newProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andExpect(status().isOk());

        assertEquals(0, productRepository.findAll().size());
    }

    @Test
    public void givenATaxRate_whenDeleteByNonExistingId_then404() throws Exception {
        taxRateRepository.save(taxRate);
        product.setId(-1000L);

        MvcResult mvcResult = this.mockMvc.perform(delete(PRODUCTS_BASE_URI + "/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    public void givenSeveralProductsWithTaxRateAndACategory_whenDeleteMultiple_verifyProductsDeleted() throws Exception {
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        Category newCategory = categoryRepository.save(category);
        product.setTaxRate(newTaxRate);
        product.setCategory(newCategory);
        product2.setName("product2");
        product2.setPrice(20.0);
        product2.setTaxRate(newTaxRate);
        product3.setName("product3");
        product3.setPrice(20.0);
        product3.setTaxRate(newTaxRate);
        Product newProduct = productRepository.save(product);
        Product newProduct2 = productRepository.save(product2);
        Product newProduct3 = productRepository.save(product3);

        assertEquals(3, productRepository.findAll().size());

        List<Long> ids = List.of(newProduct.getId(), newProduct2.getId(), newProduct3.getId());

        for (Long id : ids) {
            ResultActions mvcResult = this.mockMvc.perform(
                delete(PRODUCTS_BASE_URI + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                .andExpect(status().isOk());
        }
        assertEquals(0, productRepository.findAll().size());

    }


    @Test
    public void givenSeveralProductsWithTaxRateAndACategory_whenFindByCategory_getListWithOnlySearchedFor() throws Exception {
        TaxRate newTaxRate = taxRateRepository.save(taxRate);
        Category newCategory = categoryRepository.save(category);
        product.setTaxRate(newTaxRate);
        product.setCategory(newCategory);
        product2.setName("product2");
        product2.setPrice(20.0);
        product2.setCategory(newCategory);
        product2.setTaxRate(newTaxRate);
        product3.setName("product3");
        product3.setPrice(20.0);
        product3.setTaxRate(newTaxRate);
        Product newProduct = productRepository.save(product);
        Product newProduct2 = productRepository.save(product2);
        Product newProduct3 = productRepository.save(product3);

        MvcResult mvcResult = this.mockMvc.perform(get(PRODUCTS_BASE_URI + "/stats?categoryId=" + newCategory.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        List<Product> products = objectMapper.readValue(response.getContentAsString(),
            new TypeReference<>() {
            });
        assertAll(
            () -> assertEquals(2, products.size()),
            () -> assertEquals(newProduct, products.get(0)),
            () -> assertEquals(newProduct2, products.get(1))
        );

        MvcResult mvcResultAll = this.mockMvc.perform(get(PRODUCTS_BASE_URI + "/stats?categoryId=" + (-1L))
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseAll = mvcResultAll.getResponse();
        assertNotNull(responseAll);
        assertEquals(HttpStatus.OK.value(), responseAll.getStatus());

        List<Product> productsAll = objectMapper.readValue(responseAll.getContentAsString(),
            new TypeReference<>() {
            });
        assertAll(
            () -> assertEquals(3, productsAll.size()),
            () -> assertEquals(newProduct, productsAll.get(0)),
            () -> assertEquals(newProduct2, productsAll.get(1)),
            () -> assertEquals(newProduct3, productsAll.get(2))
        );
    }
}
