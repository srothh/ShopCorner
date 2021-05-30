package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Profile("generateData")
@Component
public class ProductDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TaxRateRepository taxRateRepository;
    private final ProductMapper productMapper;

    public ProductDataGenerator(ProductRepository productRepository, CategoryRepository categoryRepository, TaxRateRepository taxRateRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.taxRateRepository = taxRateRepository;
        this.productMapper = productMapper;
    }

    @PostConstruct
    public void generateProducts() throws IOException {
        if (false) {
            LOGGER.debug("products already generated");
        } else {
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
            TaxRate taxRate2 = this.taxRateRepository.findById(2L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            Product product1 = Product.ProductBuilder.getProductBuilder()
                .withName("Banane")
                .withDescription("leckere Bananen aus Ecuador")
                .withPrice(1.49)
                .withTaxRate(taxRate2)
                .withCategory(category3)
                .build();
            productRepository.save(product1);
            TaxRate taxRate3 = this.taxRateRepository.findById(3L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            Faker faker = new Faker(new Locale("de-AT"));
            TaxRate taxRate1 = this.taxRateRepository.findById(1L).orElseThrow(() -> new NotFoundException("Could not find tax-rate"));
            List<byte[]> img = prepareImages();
            generateProductsWithTaxRate(taxRate1, category1, category2, category3, category4, faker, img);
            generateProductsWithTaxRate(taxRate2, category1, category2, category3, category4, faker, img);
            generateProductsWithTaxRate(taxRate3, category1, category2, category3, category4, faker, img);
        }

    }

    public void generateProductsWithTaxRate(TaxRate taxRate, Category category1, Category category2, Category category3, Category category4, Faker faker, List<byte[]> img) {
        for (int i = 0; i < 10; i++) {
            Product prod = Product.ProductBuilder.getProductBuilder().withName(faker.space().nasaSpaceCraft())
                .withDescription(faker.lorem().sentence(2)).withPrice(faker.number().randomDouble(2, 1, 200)).withTaxRate(taxRate).withCategory(category1)
                .withPicture(img.get(i % (img.size()))).build();
            productRepository.save(prod);
            Product prod1 = Product.ProductBuilder.getProductBuilder().withName(faker.food().ingredient())
                .withDescription(faker.lorem().sentence(2)).withPrice(faker.number().randomDouble(2, 1, 200)).withTaxRate(taxRate).withCategory(category2)
                .withPicture(img.get((i + 1) % (img.size()))).build();
            productRepository.save(prod1);
            Product prod2 = Product.ProductBuilder.getProductBuilder().withName(faker.food().spice())
                .withDescription(faker.lorem().sentence(2)).withPrice(faker.number().randomDouble(2, 1, 200)).withTaxRate(taxRate).withCategory(category3)
                .withPicture(img.get((i + 2) % (img.size()))).build();
            productRepository.save(prod2);
            Product prod3 = Product.ProductBuilder.getProductBuilder().withName(faker.food().vegetable())
                .withDescription(faker.lorem().sentence(2)).withPrice(faker.number().randomDouble(2, 1, 200)).withTaxRate(taxRate).withCategory(category4)
                .withPicture(img.get((i + 3) % (img.size()))).build();
            productRepository.save(prod3);
        }
    }

    public List<byte[]> prepareImages() throws IOException {
        List<byte[]> list = new ArrayList<>();
        byte[] img = FileUtils.readFileToByteArray(new File("src/main/resources/Images/apple.jpg"));
        list.add(img);
        byte[] img2 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/banana.png"));
        list.add(img2);
        byte[] img3 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/bread.jpg"));
        list.add(img3);
        byte[] img4 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/broccoli.jpg"));
        list.add(img4);
        byte[] img5 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/grapes.jpg"));
        list.add(img5);
        byte[] img6 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/paprika.jpg"));
        list.add(img6);
        byte[] img7 = FileUtils.readFileToByteArray(new File("src/main/resources/Images/pear.png"));
        list.add(img7);

        return list;
    }
}
