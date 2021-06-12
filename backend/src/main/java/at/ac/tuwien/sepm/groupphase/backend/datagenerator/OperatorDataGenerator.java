package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.Locale;

@Profile("generateData")
@Component
public class OperatorDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    public OperatorDataGenerator(OperatorRepository operatorRepository, EncoderConfig encoderConfig) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = encoderConfig.passwordEncoder();
    }

    @PostConstruct
    public void generateOperators() {
        if (operatorRepository.findAll().size() > 0) {
            LOGGER.debug("operators already generated");
        } else {
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < 10; i++) {
                //unique constraints get skipped
                try {
                    operatorRepository.save(new Operator(faker.name().fullName(), faker.name().username(), passwordEncoder.encode(faker.internet().password(8, 20, true, true, true)),
                        faker.internet().emailAddress(), Permissions.admin));
                } catch (DataIntegrityViolationException e) {
                    continue;
                }

            }
            for (int i = 0; i < 90; i++) {
                try {
                    operatorRepository.save(new Operator(faker.name().fullName(), faker.name().username(), passwordEncoder.encode(faker.internet().password(8, 20, true, true, true)),
                        faker.internet().emailAddress(), Permissions.employee));
                } catch (DataIntegrityViolationException e) {
                    continue;
                }

            }
            Operator operator = new Operator("test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            String password = passwordEncoder.encode(operator.getPassword());
            operator.setPassword(password);
            operatorRepository.save(operator);
        }
    }
}