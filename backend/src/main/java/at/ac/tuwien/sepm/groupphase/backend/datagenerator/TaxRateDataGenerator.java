package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

@Profile("generateData")
@Component
public class TaxRateDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final Map<Double, Double> TAX_RATES = Map.of(10.0, 1.10, 13.00, 1.13, 20.0, 1.20);
    private final TaxRateRepository taxRateRepository;

    public TaxRateDataGenerator(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    @PostConstruct
    public void generateTaxRates() {
        if (taxRateRepository.findAll().size() > 0) {
            LOGGER.debug("taxes already generated");
        } else {
            for (Map.Entry<Double, Double> entry : TAX_RATES.entrySet()) {
                TaxRate taxRate = TaxRate.TaxRateBuilder.getTaxRateBuilder()
                    .withPercentage(entry.getKey())
                    .withCalculationFactor(entry.getValue())
                    .build();
                taxRateRepository.save(taxRate);
            }

        }

    }

}
