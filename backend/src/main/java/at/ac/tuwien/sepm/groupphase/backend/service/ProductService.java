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



}
