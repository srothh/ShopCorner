package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new category that is eventually associated to a product.
     *
     * @param category the newly added product
     * @return the newly created category
     */
    @Override
    public Category createCategory(Category category) {
        LOGGER.trace("create new Category({})", category);
        return categoryRepository.save(category);
    }

    /**
     * Gets all Categories that were previously created.
     *
     * @return all categories that were previously saved
     */
    @Override
    public List<Category> getAllCategories() {
        LOGGER.trace("retrieve all categories");
        return this.categoryRepository.getAllCategories();
    }
}
