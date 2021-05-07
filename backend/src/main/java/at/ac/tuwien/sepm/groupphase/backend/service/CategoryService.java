package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

public interface CategoryService {
    /**
     * Creates a new category that is eventually associated to a product.
     *
     * @param category the newly added product
     * @return the newly created category
     */
    Category createCategory(Category category);

}
