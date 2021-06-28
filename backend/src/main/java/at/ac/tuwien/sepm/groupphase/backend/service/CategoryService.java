package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import org.springframework.data.domain.Page;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;


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

    /**
     * Updates an already existing category entity in the database with the given Id.
     *
     * @param categoryId the id of the category to execute the update
     * @param category the new category with updated fields
     * @throws RuntimeException upon encountering errors with the database
     */
    void updateCategory(Long categoryId, Category category);

    /**
     * Deletes a category entity in the database with the given Id.
     *
     * @param categoryId the id of the category to execute the delete action
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCategory(Long categoryId);

    /**
     * Gets the category entity  with the given Id.
     *
     * @param categoryId the id of the category to retrieve from the database
     * @return the requested category specified by the id
     * @throws NotFoundException is being thrown when the request category is not found
     * @throws RuntimeException is being thrown if some error occurs during data processing
     *
     */
    Category getCategoryById(Long categoryId);



}
