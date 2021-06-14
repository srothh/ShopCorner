package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoryEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private final Category category = new Category();
    private final Category category2 = new Category();
    private final Category category3 = new Category();
    private final Product product = new Product();
    @BeforeEach
    public void beforeEach() {
        categoryRepository.deleteAll();
        category.setName(TEST_CATEGORY_NAME);
    }
    @Test
    public void givenNothing_whenPost_thenCategoryWithAllSetPropertiesPlusId() throws Exception {
        CategoryDto categoryDto = categoryMapper.entityToDto(category);
        String body = objectMapper.writeValueAsString(categoryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CATEGORY_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CategoryDto categoryResponse = objectMapper.readValue(response.getContentAsString(),
            CategoryDto.class);

        assertNotNull(categoryResponse.getId());
        assertNotNull(categoryResponse.getName());
        categoryResponse.setId(null);
        assertEquals(category, categoryMapper.dtoToEntity(categoryResponse));
    }
    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        category.setName("Too Long String - Too Long String - Too Long String - Too Long String");

        CategoryDto categoryDto = categoryMapper.entityToDto(category);
        String body = objectMapper.writeValueAsString(categoryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(CATEGORY_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
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
                assertEquals(1, errors.length);
            }
        );
    }
    @Test
    public void givenACategory_whenPut_thenVerifyCategoryChanged() throws Exception{
        Category newCategory = categoryRepository.save(category);
        newCategory.setName("changedCategory");
        CategoryDto categoryDto = categoryMapper.entityToDto(newCategory);
        String body = objectMapper.writeValueAsString(categoryDto);
        ResultActions mvcResult = this.mockMvc.perform(
            put(CATEGORY_BASE_URI + '/' + newCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
                .content(body))
            .andExpect(status().isOk());

        MockHttpServletResponse response = mvcResult.andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        Category updatedCategory = this.categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException("Error"));
        assertEquals(newCategory.getName(),updatedCategory.getName());
    }
    @Test
    public void givenACategory_whenPutByNonExistingId_then400() throws Exception {
        categoryRepository.save(category);
        category.setId(-100L);
        CategoryDto categoryDto = categoryMapper.entityToDto(category);
        String body = objectMapper.writeValueAsString(categoryDto);
        ResultActions mvcResult = this.mockMvc.perform(
        put(CATEGORY_BASE_URI + "/" + category.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .content(body))
            .andExpect(status().isNotFound());
    }
    @Test
    public void givenACategory_whenPutIllegalArgument_then400() throws Exception{
        Category newCategory = categoryRepository.save(category);
        newCategory.setName("       ");
        CategoryDto categoryDto = categoryMapper.entityToDto(newCategory);
        String body = objectMapper.writeValueAsString(categoryDto);

        ResultActions mvcResult = this.mockMvc.perform(
            put(CATEGORY_BASE_URI + "/" + newCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest());
        MockHttpServletResponse response = mvcResult.andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    @Test
    public void givenACategory_whenDelete_thenVerifyCategoryDeleted() throws Exception {

        Category newCategory = categoryRepository.save(category);

        assertEquals(1, this.categoryRepository.findAll().size());

        ResultActions mvcResult = this.mockMvc.perform(
            delete(CATEGORY_BASE_URI + "/" + newCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andExpect(status().isOk());

        assertEquals(0, categoryRepository.findAll().size());
    }
    @Test
    public void givenSeveralCategories_whenDeleteMultiple_thenVerifyCategoriesDeleted() throws Exception {

        Category newCategory = categoryRepository.save(category);
        category2.setName("Cat2");
        category3.setName("Cat3");
        Category newCategory2 = categoryRepository.save(category2);
        Category newCategory3 = categoryRepository.save(category3);

        assertEquals(3, this.categoryRepository.findAll().size());

        List<Long> ids = List.of(newCategory.getId(), newCategory2.getId(), newCategory3.getId());

        for (Long id: ids) {
            ResultActions mvcResult = this.mockMvc.perform(
                delete(CATEGORY_BASE_URI + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
                .andExpect(status().isOk());
        }
        assertEquals(0,categoryRepository.findAll().size());
    }

}
