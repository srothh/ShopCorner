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
     * @param categoryId assigns a product to its category
     * @param taxRateId assigns a specific tax-rate to a product
     * @return the newly created product
     *
     */
    Product createProduct(Product product, Long categoryId, Long taxRateId);

    /**
     * Gets all products that were previously added in the database.
     *
     * @return all products that are currently saved in the database
     */
    List<Product> getAllProducts();

    /**
     * Updates an already existing product in the database.
     *
     * @param productId the Id of the product to be updated
     * @param product the newly updated product entity with the updated fields
     * @param categoryId an optional categoryId to associate the new product with a category
     * @param taxRateId a possibly updated taxRateId to associate the new product
     *
     */
    void updateProduct(Long productId, Product product, Long categoryId, Long taxRateId);


    /**
     * Gets a specific product with the given productId.
     *
     * @param productId the is to search in the database
     *
     * @return the product entity with the given product Id
     *
     * */
    Product getProductById(Long productId);




}
