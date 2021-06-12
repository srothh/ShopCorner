package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
            Set<String> loginNames = new HashSet<>();
            Set<String> emails = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                //unique constraints get skipped
                String email = faker.internet().emailAddress();
                String loginName = faker.name().username();
                if (loginNames.add(loginName) && emails.add(email)) {
                    operatorRepository.save(new Operator(faker.name().fullName(), loginName, passwordEncoder.encode(faker.internet().password(8, 20, true, true, true)),
                        email, Permissions.admin));
                }

            }
            for (int i = 0; i < 90; i++) {
                String email = faker.internet().emailAddress();
                String loginName = faker.name().username();
                if (loginNames.add(loginName) && emails.add(email)) {

                    operatorRepository.save(new Operator(faker.name().fullName(), loginName, passwordEncoder.encode(faker.internet().password(8, 20, true, true, true)),
                        email, Permissions.employee));
                }

            }
            Operator operator = new Operator("test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            String password = passwordEncoder.encode(operator.getPassword());
            operator.setPassword(password);
            operatorRepository.save(operator);
        }
    }
}