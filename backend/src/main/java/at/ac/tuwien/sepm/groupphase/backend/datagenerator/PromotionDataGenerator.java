package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import at.ac.tuwien.sepm.groupphase.backend.repository.PromotionRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Profile("generateData")
@Component
public class PromotionDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionDataGenerator(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @PostConstruct
    public void generatePromotions() {
        if (promotionRepository.findAll().size() > 0) {
            LOGGER.debug("Promotions already generated");
        } else {
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < 200; i++) {
                promotionRepository.save(
                    new Promotion(faker.lorem().word(), faker.number().randomDouble(2, 1, 100), LocalDate.now(), faker.date().future(100, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), faker.lorem().word(),
                        faker.number().randomDouble(2, 5, 150)));
            }
        }
    }
}
