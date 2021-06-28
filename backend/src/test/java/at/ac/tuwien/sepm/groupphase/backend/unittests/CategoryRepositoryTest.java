package at.ac.tuwien.sepm.groupphase.backend.unittests;


import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest implements TestData {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void givenNothing_whenSaveCategory_thenFindListWithOneElementAndFindProductById(){
        Category category = new Category();
        category.setName(TEST_CATEGORY_NAME);
        categoryRepository.save(category);
        assertAll(
            () -> assertEquals(1, categoryRepository.findAll().size()),
            () -> assertNotNull(categoryRepository.findById(category.getId()))
        );
    }
    @Test
    void givenNothing_whenSaveInvalidCategoryOnlyWhiteSpaces_then400() {
        Category category = new Category();
        category.setName("       ");
        assertThrows( ConstraintViolationException.class, () -> categoryRepository.save(category));
    }

}
