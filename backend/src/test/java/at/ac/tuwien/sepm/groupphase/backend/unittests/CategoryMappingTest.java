package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryMappingTest implements TestData {
    private final Category category = new Category();

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void givenNothing_whenMapCategoryDtoToEntity_thenEntityHasAllProperties(){
      category.setId(0L);
      category.setName(TEST_CATEGORY_NAME);

      CategoryDto categoryDto = categoryMapper.entityToDto(category);

        assertAll(
            () -> assertEquals(TEST_CATEGORY_ID, categoryDto.getId()),
            () -> assertEquals(TEST_CATEGORY_NAME, categoryDto.getName())
        );


    }

}
