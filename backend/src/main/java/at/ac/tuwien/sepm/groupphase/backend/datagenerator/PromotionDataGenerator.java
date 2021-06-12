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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
            LOGGER.info(new Timestamp(System.currentTimeMillis()).toString());
            Set<String> contains = new HashSet<>();
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < 300; i++) {
                String code = faker.lorem().word() + faker.lorem().word();
                if (contains.add(code)) {
                    promotionRepository.save(
                        new Promotion(faker.lorem().word(), faker.number().randomDouble(2, 1, 1000), LocalDateTime.now().withNano(0), LocalDateTime
                            .of(faker.number().numberBetween(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + faker.number().numberBetween(1, 5)), faker.number().numberBetween(1, 12),
                                faker.number().numberBetween(1, 30),
                                faker.number().numberBetween(1, 23), faker.number().numberBetween(1, 59), faker.number().numberBetween(0, 59)), code,
                            faker.number().randomDouble(2, 5, 150)));
                }

            }
        }
    }


}

