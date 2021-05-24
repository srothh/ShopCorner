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

import java.lang.invoke.MethodHandles;
import java.util.List;

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

    @Override
    public Product createProduct(Product product, Long categoryId, Long taxRateId) {
        LOGGER.trace("create new Product({})" + "  category({})"  + " taxRateId({})", product, categoryId, taxRateId);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        if (categoryId != null) {
            assignProductToCategory(product, categoryId);

        }
        assignProductToTaxRate(product, taxRateId);
        return this.productRepository.save(product);
    }

    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        LOGGER.trace("assigning categoryId({}) to  product", categoryId);
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Could not find category!"));
        product.setCategory(category);
    }

    private void assignProductToTaxRate(Product product, Long taxRateId) {
        LOGGER.trace("assigning taxRateId({}) to  product", taxRateId);
        TaxRate taxRate = taxRateRepository.findById(taxRateId)
            .orElseThrow(() -> new NotFoundException("Could not find tax-rate!"));
        product.setTaxRate(taxRate);
    }

    @Override
    public List<Product> getAllProducts() {
        LOGGER.trace("retrieving all products");
        return this.productRepository.findAll();
    }

    public void validateProperty(String description) {
        LOGGER.trace("validate property({}) for a product", description);
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Only whiteSpace not allowed!");
        }
        if (description.trim().length() > 70) {
            throw new IllegalArgumentException("description is too long");
        }
    }
}
