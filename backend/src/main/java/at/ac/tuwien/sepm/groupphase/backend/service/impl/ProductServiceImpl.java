package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
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

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product, Long categoryId) throws Exception {
        validateProduct(product);
        if (categoryId != null) {
            assignProductToCategory(product, categoryId);
        }
        return this.productRepository.save(product);
    }

    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Could not find category!"));
        //String query = "SELECT * FROM Category c WHERE c.id= :categoryId";
        //TypedQuery<Category> typedQuery = EntityManager.
        product.setCategory(category);

    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        return this.productRepository.findAll();
    }


    //NOTE: this can be extracted to a validator class -> waiting to see how teammates have implemented this
    public void validateProduct(Product product) throws Exception {
        //name is mandatory!
        if (product.getName() == null) {
            throw new Exception("name cannot be null!");
        }

        //a string with only whitespaces not allowed
        if (product.getName().trim().isEmpty()) {
            throw new Exception("name consist of only whitespaces");
        }
        //maximum of characters exceeded!
        if (product.getName().length() > 20) {
            throw new Exception("name is too long!");

        }
        if (product.getDescription() != null) {
            if (product.getDescription().trim().isEmpty()) {
                throw new Exception("description consist of only whitespaces");
            }
            if (product.getDescription().length() > 50) {
                throw new Exception("description is too long!");
            }

        }

    }
}
