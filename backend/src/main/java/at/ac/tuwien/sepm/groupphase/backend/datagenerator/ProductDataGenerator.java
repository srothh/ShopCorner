package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class ProductDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TaxRateRepository taxRateRepository;

    public ProductDataGenerator(ProductRepository productRepository, CategoryRepository categoryRepository, TaxRateRepository taxRateRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.taxRateRepository = taxRateRepository;
    }

    @PostConstruct
    public void generateProducts() {
        if (productRepository.findAll().size() == 0) {
            TaxRate taxRate1 = this.taxRateRepository.findById(1L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            TaxRate taxRate2 = this.taxRateRepository.findById(2L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            TaxRate taxRate3 = this.taxRateRepository.findById(3L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            Category category1 = Category.CategoryBuilder.getCategoryBuilder()
                .withName("IT & Elektronik")
                .build();
            categoryRepository.save(category1);
            Category category2 = Category.CategoryBuilder.getCategoryBuilder()
                .withName("Möbel")
                .build();
            categoryRepository.save(category2);
            Category category3 = Category.CategoryBuilder.getCategoryBuilder()
                .withName("Obst & Gemüse")
                .build();
            categoryRepository.save(category3);
            Category category4 = Category.CategoryBuilder.getCategoryBuilder()
                .withName("Kleidung")
                .build();
            categoryRepository.save(category4);
            Product product1 = Product.ProductBuilder.getProductBuilder()
                .withName("Banane")
                .withDescription("leckere Bananen aus Ecuador")
                .withPrice(1.49)
                .withTaxRate(taxRate2)
                .withCategory(category3)
                .build();
            productRepository.save(product1);
        }
    }
}
