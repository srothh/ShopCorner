package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TaxRateRepository taxRateRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, TaxRateRepository taxRateRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.taxRateRepository = taxRateRepository;
    }


    /**
     * Creates a new product.
     *
     * @param product    the newly added product
     * @param categoryId assigns a product to its category
     * @param taxRateId  assigns a specific tax-rate to a product
     * @return the newly created product
     */
    @Override
    public Product createProduct(Product product, Long categoryId, Long taxRateId) {
        LOGGER.trace("create new Product({})" + "  category({})" + " taxRateId({})", product, categoryId, taxRateId);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        if (categoryId != null) {
            assignProductToCategory(product, categoryId);

        }
        assignProductToTaxRate(product, taxRateId);
        return this.productRepository.save(product);
    }

    /**
     * assigns a product to a category.
     *
     * @param product    the product that will set its category-relationship to valid category
     * @param categoryId the id of a category to find the entity in the repository
     * @throws NotFoundException if the id of the category is not found in the database
     */
    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        LOGGER.trace("assigning categoryId({}) to  product", categoryId);
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Could not find category!"));
        product.setCategory(category);
    }

    /**
     * assigns a product to a tax-rate.
     *
     * @param product   the product that will set its tax-rate-relationship to valid tax-rate
     * @param taxRateId the id of a tax-rate to find the entity in the repository
     * @throws NotFoundException if the id of the tax-rate is not found in the database
     */

    private void assignProductToTaxRate(Product product, Long taxRateId) {
        LOGGER.trace("assigning taxRateId({}) to  product", taxRateId);
        TaxRate taxRate = taxRateRepository.findById(taxRateId)
            .orElseThrow(() -> new NotFoundException("Could not find tax-rate!"));
        product.setTaxRate(taxRate);
    }

    /**
     * Gets all products that were previously added in the database.
     *
     * @return all products that are currently saved in the database
     */

    @Override
    public List<Product> getAllProducts() {
        LOGGER.trace("retrieving all products");
        return this.productRepository.findAll();
    }

    /**
     * validates a property of product -> NOTE: this was implemented because annotations do not support optional parameters that is necessary
     * for some properties.
     *
     * @param description check if the trimmed string is not empty and the size does not exceed 70 characters
     */

    public void validateProperty(String description) {
        LOGGER.trace("validate property({}) for a product", description);
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Only whiteSpace not allowed!");
        }
        if (description.trim().length() > 70) {
            throw new IllegalArgumentException("description is too long");
        }
    }

    /**
     * Updates an already existing product in the database.
     *
     * @param productId the Id of the product to be updated
     * @param product the newly updated product entity with the updated fields
     * @param categoryId an optional categoryId to associate the new product with a category
     * @param taxRateId a possibly updated taxRateId to associate the new product
     *
     */

    public void updateProduct(Long productId, Product product, Long categoryId, Long taxRateId) {
        LOGGER.trace("update Product with({})" + "  category({})" + " taxRateId({})", product, categoryId, taxRateId);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        Product updateProduct = this.productRepository
            .findById(productId).orElseThrow(() -> new NotFoundException("Could not find product with Id:" + productId));
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());

        if (categoryId != null) {
            assignProductToCategory(updateProduct, categoryId);
        }
        assignProductToTaxRate(updateProduct, taxRateId);
        this.productRepository.save(updateProduct);
    }
}
