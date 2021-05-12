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

@Profile("generateData")
@Component
public class TaxRateDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final List<Double> TAX_RATES = List.of(1.10, 1.13, 1.20);
    private final TaxRateRepository taxRateRepository;

    public TaxRateDataGenerator(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    @PostConstruct
    public void generateTaxRates() {
        if (taxRateRepository.findAll().size() > 0) {
            LOGGER.debug("taxes already generated");
        } else {
            for (Double taxPercentage : TAX_RATES) {
                TaxRate taxRate = TaxRate.TaxRateBuilder.getTaxRateBuilder()
                    .withPercentage(taxPercentage)
                    .build();
                taxRateRepository.save(taxRate);
            }
        }
    }

}
