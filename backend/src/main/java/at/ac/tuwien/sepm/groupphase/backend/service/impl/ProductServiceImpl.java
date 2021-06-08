package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TaxRateRepository taxRateRepository;
    private final InvoiceItemService invoiceItemService;

    @Autowired
    public ProductServiceImpl(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        TaxRateRepository taxRateRepository,
        InvoiceItemService invoiceItemService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.taxRateRepository = taxRateRepository;
        this.invoiceItemService = invoiceItemService;
    }

    @Caching(evict = {
        @CacheEvict(value = "productPages", allEntries = true),
        @CacheEvict(value = "counts", key = "'products'"),
        @CacheEvict(value = "categoryCounts", allEntries = true)
    })
    @Override
    public Product createProduct(Product product) {
        LOGGER.trace("createProduct({})", product);
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

    @Caching(evict = {
        @CacheEvict(value = "productPages", allEntries = true),
        @CacheEvict(value = "categoryCounts", allEntries = true)
    })
    @Transactional
    public void assignProductToCategory(Product product, Long categoryId) {
        LOGGER.trace("assignProductToCategory({},{})", product, categoryId);
        if (categoryId == null) {
            product.setCategory(null);
        } else {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Could not find category!"));
            product.setCategory(category);

        }
    }

    private void assignProductToTaxRate(Product product, Long taxRateId) {
        LOGGER.trace("assignProductToTaxRate({}{})", product, taxRateId);
        TaxRate taxRate = taxRateRepository.findById(taxRateId)
            .orElseThrow(() -> new NotFoundException("Could not find tax-rate!"));
        product.setTaxRate(taxRate);
    }

    @Cacheable(value = "productPages")
    @Override
    public Page<Product> getAllProductsPerPage(int page, int pageCount, Long categoryId, String sortBy, String name) {
        LOGGER.trace("getAllProductsPerPage({}, {})", page, pageCount);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable pages;
        if (sortBy.equals("id")) {
            pages = PageRequest.of(page, pageCount);
        } else {
            pages = PageRequest.of(page, pageCount, Sort.by(sortBy).descending());
        }
        if (!name.isEmpty() && categoryId == -1) {
            return this.productRepository.findAllByName(name, pages);
        } else if (!name.isEmpty() && categoryId > 0) {
            return this.productRepository.findAllByNameAndCategoryId(name, categoryId, pages);
        } else if (name.isEmpty() && categoryId > 0) {
            return this.productRepository.findAllByCategoryId(categoryId, pages);
        }

        return this.productRepository.findAll(pages);
    }

    @Override
    public List<Product> getAllProducts() {
        LOGGER.trace("getAllProducts()");
        return this.productRepository.findAll();
    }

    public void validateProperty(String description) {
        LOGGER.trace("validateProperty({})", description);
        if (!description.isEmpty()) {
            if (description.trim().isEmpty()) {
                throw new IllegalArgumentException("Only whiteSpaces not allowed!");
            }
        }
        if (description.trim().length() > 70) {
            throw new IllegalArgumentException("description is too long");
        }
    }

    @Caching(evict = {
        @CacheEvict(value = "categoryCounts", allEntries = true),
        @CacheEvict(value = "productPages", allEntries = true)
    })
    public void updateProduct(Long productId, Product product) {
        LOGGER.trace("updateProduct({})", product);
        if (product.getDescription() != null) {
            this.validateProperty(product.getDescription());
        }
        Product updateProduct = this.productRepository
            .findById(productId).orElseThrow(() -> new NotFoundException("Could not find product with Id:" + productId));
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setLocked(product.isLocked());
        updateProduct.setPicture(product.getPicture());
        Category updateCategory = product.getCategory();
        TaxRate updateTaxRate = product.getTaxRate();
        assignProductToCategory(updateProduct, updateCategory.getId());
        assignProductToTaxRate(updateProduct, updateTaxRate.getId());
        this.productRepository.save(updateProduct);
    }

    public Product findById(Long productId) {
        LOGGER.trace("findById{}", productId);
        return productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(String.format("Could not find product with id: %s", productId)));
    }

    @Cacheable(value = "counts", key = "'products'")
    @Override
    public Long getProductsCount() {
        LOGGER.trace("getProductsCount()");
        return productRepository.count();
    }

    @Caching(evict = {
        @CacheEvict(value = "productPages", allEntries = true),
        @CacheEvict(value = "counts", key = "'products'"),
        @CacheEvict(value = "categoryCounts", allEntries = true)
    })
    @Override
    public void deleteProductById(Long productId) {
        LOGGER.trace("deleteProductById{}", productId);
        boolean softDelete = false;
        Product productToDelete = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(String.format("Could not find product with id: %s", productId)));
        softDelete = this.invoiceItemService.findAllInvoicesItems().stream()
            .map(InvoiceItem::getProduct)
            .anyMatch(product -> product.getId().equals(productId));

        if (!softDelete) {
            productRepository.deleteById(productId);
        } else {
            productToDelete.setDeleted(true);
            productRepository.save(productToDelete);
        }

    }

    @Override
    @Cacheable(value = "categoryCounts", key = "#category")
    public Long getCountByCategory(Page page, Long category) {
        return page.getTotalElements();
    }


}
