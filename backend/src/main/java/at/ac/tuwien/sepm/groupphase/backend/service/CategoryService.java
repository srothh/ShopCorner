package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    /**
     * Creates a new category that is eventually associated to a product.
     *
     * @param category the newly added product
     * @return the newly created category
     */
    Category createCategory(Category category);


    /**
     * Gets all Categories that were previously created.
     * @return all categories that were previously saved
     */
    List<Category> getAllCategories();
}
