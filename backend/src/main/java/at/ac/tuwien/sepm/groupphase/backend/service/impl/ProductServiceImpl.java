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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Product createProduct(Product product) {
        LOGGER.trace("create new Product({})", product);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        if (product.getCategory() != null) {
            Category category = product.getCategory();
            assignProductToCategory(product, category.getId());

        }
        TaxRate taxRate = product.getTaxRate();
        assignProductToTaxRate(product, taxRate.getId());
        return this.productRepository.save(product);
    }

    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        LOGGER.trace("assigning categoryId({}) to  product", categoryId);
        if (categoryId == null) {
            product.setCategory(null);
        } else {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Could not find category!"));
            product.setCategory(category);

        }
    }

    private void assignProductToTaxRate(Product product, Long taxRateId) {
        LOGGER.trace("assigning taxRateId({}) to  product", taxRateId);
        TaxRate taxRate = taxRateRepository.findById(taxRateId)
            .orElseThrow(() -> new NotFoundException("Could not find tax-rate!"));
        product.setTaxRate(taxRate);
    }

    @Override
    public Page<Product> getAllProductsPerPage(int page, int pageCount) {
        LOGGER.trace("retrieving all products in a paginated manner");
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable pages = PageRequest.of(page, pageCount);
        return this.productRepository.findAll(pages);
    }

    @Override
    public List<Product> getAllProducts() {
        LOGGER.trace("retrieving all products");
        return this.productRepository.findAll();
    }

    public void validateProperty(String description) {
        LOGGER.trace("validate property({}) for a product", description);
        if (!description.isEmpty()) {
            if (description.trim().isEmpty()) {
                throw new IllegalArgumentException("Only whiteSpaces not allowed!");
            }
        }
        if (description.trim().length() > 70) {
            throw new IllegalArgumentException("description is too long");
        }
    }

    public void updateProduct(Long productId, Product product) {
        LOGGER.trace("update Product with({})", product);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        Product updateProduct = this.productRepository
            .findById(productId).orElseThrow(() -> new NotFoundException("Could not find product with Id:" + productId));
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setPicture(product.getPicture());
        Category updateCategory = product.getCategory();
        TaxRate updateTaxRate = product.getTaxRate();
        assignProductToCategory(updateProduct, updateCategory.getId());
        assignProductToTaxRate(updateProduct, updateTaxRate.getId());
        this.productRepository.save(updateProduct);
    }

    public Product findById(Long productId) {
        LOGGER.trace("find product with Id{}", productId);
        return productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(String.format("Could not find product %s", productId)));
    }

    @Override
    public int getProductsCount() {
        LOGGER.trace("get the number of products");
        return productRepository.findAll().size();
    }

    @Override
    public void deleteProductById(Long productId) {
        LOGGER.trace("DELETE Product with Id{}", productId);
        if (findById(productId) != null) {
            productRepository.deleteById(productId);
        }
    }


}
