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
import java.util.Locale;

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
            Calendar cal = Calendar.getInstance();
            LOGGER.info(new Timestamp(System.currentTimeMillis()).toString());
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < 200; i++) {
                promotionRepository.save(
                    new Promotion(faker.lorem().word(), faker.number().randomDouble(2, 1, 100), LocalDateTime.now().withNano(0), LocalDateTime
                        .of(faker.number().numberBetween(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + 2), faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 30),
                            faker.number().numberBetween(1, 23), faker.number().numberBetween(1, 59), faker.number().numberBetween(0, 59)), faker.lorem().word(),
                        faker.number().randomDouble(2, 5, 150)));
            }
        }
    }


}

