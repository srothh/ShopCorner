package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.util.Map;

@Configuration
@Profile("generateData")
public class ProductDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DataSource source;
    private final ProductRepository productRepository;
    private final TaxRateRepository taxRateRepository;
    private static final Map<Double, Double> TAX_RATES = Map.of(10.0, 1.10, 13.00, 1.13, 20.0, 1.20);

    public ProductDataGenerator(DataSource source, ProductRepository productRepository, TaxRateRepository taxRateRepository) {
        this.source = source;
        this.productRepository = productRepository;
        this.taxRateRepository = taxRateRepository;
    }

    @PostConstruct
    void generateProducts() {
        if (productRepository.findAll().size() > 0) {
            LOGGER.debug("products already generated");
        } else {
            TaxRate t = null;
            if (taxRateRepository.findAll().size() > 0) {
                LOGGER.debug("taxes already generated");
            } else {
                for (Map.Entry<Double, Double> entry : TAX_RATES.entrySet()) {
                    TaxRate taxRate = TaxRate.TaxRateBuilder.getTaxRateBuilder()
                        .withPercentage(entry.getKey())
                        .withCalculationFactor(entry.getValue())
                        .build();
                    t = taxRateRepository.save(taxRate);
                }
            }

            Product product1 = new Product();
            product1.setName("Apfel");
            product1.setPrice(1.99);
            product1.setTaxRate(t);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("Birne");
            product2.setPrice(1.99);
            product2.setTaxRate(t);
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("Ananas");
            product3.setPrice(1.99);
            product3.setTaxRate(t);
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("K");
            product4.setPrice(1.99);
            product4.setTaxRate(t);
            productRepository.save(product4);
        }
    }
}
