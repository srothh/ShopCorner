package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
/**
 * Service class handling business logic for Categories.
 * */

public interface CategoryService {
    /**
     * Creates a new category that is eventually associated to a product.
     *
     * @param category the newly added product
     * @return the newly created category
     * @throws RuntimeException occurs during database operations
     */
    Category createCategory(Category category);

    /**
     * Gets all Categories that were previously created in a  paginated Manner.
     *
     * @param page the current page
     * @param pageSize number of entries per page
     *
     * @return all categories that were previously saved in a page Object given by page and pageSize
     * @throws RuntimeException occurs during database operations
     */
    Page<Category> getAllCategoriesPerPage(int page, int pageSize);

    /**
     * Gets all Categories that were previously created.
     *
     * @return all categories that were previously saved
     * @throws RuntimeException occurs during database operations
     */
    List<Category> getAllCategories();

    /**
     * Gets the total number of categories.
     *
     * @return the number of total categories
     * @throws RuntimeException occurs during database operations
     * */
    Long getCategoriesCount();
}
