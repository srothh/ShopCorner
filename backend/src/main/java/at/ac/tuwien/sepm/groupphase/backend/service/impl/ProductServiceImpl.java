package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
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

    @Override
    public Product createProduct(Product product, Long categoryId, Long taxRateId){
        validateProduct(product);
        if (categoryId != null) {
            assignProductToCategory(product, categoryId);

        }
        assignProductToTaxRate(product, taxRateId);
        return this.productRepository.save(product);
    }


    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Could not find category!"));
        product.setCategory(category);
    }

    private void assignProductToTaxRate(Product product, Long taxRateId) {
        TaxRate taxRate = taxRateRepository.findById(taxRateId)
            .orElseThrow(() -> new NotFoundException("Could not find tax-rate!"));
        product.setTaxRate(taxRate);
    }


    @Override
    public List<Product> getAllProducts() throws Exception {
        return this.productRepository.findAll();
    }


    //NOTE: this can be extracted to a validator class -> waiting to see how teammates have implemented this
    public void validateProduct(Product product){
        //name is mandatory!
        if (product.getName() == null) {
            throw new IllegalArgumentException("name is mandatory");
        }

        //a string with only whitespaces not allowed
        if (product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("only whitespace not allowed");
        }
        //maximum of characters exceeded!
        if (product.getName().length() > 20) {
            throw new IllegalArgumentException("name is too long");

        }
        if (product.getDescription() != null) {
            if (!product.getDescription().trim().isEmpty()) {
                if (product.getDescription().length() > 50) {
                    throw new IllegalArgumentException("description is too long");
                }
            }

        }

    }
}
