package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import java.util.List;
/**
 * Service class handling business logic for products.
 * */

public interface ProductService {
    /**
     * Creates a new product.
     *
     * @param product the newly added product
     * @return the newly created product
     *
     */
    Product createProduct(Product product);

    /**
     * Gets all products that were previously added in the database.
     *
     * @return all products that are currently saved in the database
     */
    List<Product> getAllProducts();

    /**
     * Updates an already existing product in the database.
     *
     * @param productId the Id of the product to update
     * @param product the newly updated product entity with the updated fields
     *
     */
    void updateProduct(Long productId, Product product);

    /**
     * Gets a specific product with the given productId.
     *
     * @param productId the is to search in the database
     *
     * @return the product entity with the given product Id
     *
     * */
    Product findById(Long productId);






}
