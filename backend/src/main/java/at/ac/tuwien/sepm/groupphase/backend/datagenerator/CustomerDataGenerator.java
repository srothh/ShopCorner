package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.Locale;

@Profile("generateData")
@Component
public class CustomerDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerDataGenerator(CustomerRepository customerRepository, AddressRepository addressRepository, EncoderConfig encoderConfig) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = encoderConfig.passwordEncoder();
    }

    @PostConstruct
    public void generateOperators() {
        if (customerRepository.findAll().size() > 0) {
            LOGGER.debug("customers already generated");
        } else {
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < 300; i++) {
                Address add = addressRepository.save(new Address(faker.address().streetName(), 1000, faker.address().buildingNumber(), faker.number().numberBetween(0, 10), faker.address().buildingNumber()));
                customerRepository.save(new Customer(faker.internet().emailAddress(), passwordEncoder.encode(faker.internet().password(8, 15, true, false)), faker.name().firstName(), faker.name().username(),
                    add, faker.phoneNumber().cellPhone()));
            }

        }
    }
}
