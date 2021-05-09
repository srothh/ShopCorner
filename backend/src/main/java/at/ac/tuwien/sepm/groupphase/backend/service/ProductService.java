package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;


import java.util.List;

public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param product the newly added product
     * @return the newly created product
     * @throws Exception if validation of a product has failed
     */
    Product createProduct(Product product) throws Exception;

    /**
     * Gets all products that were previously added in the database.
     *
     *
     * @return all products that are currently saved in the database
     * @throws Exception if something went wrong during during database access
     */
    List<Product> getAllProducts() throws Exception;
}
