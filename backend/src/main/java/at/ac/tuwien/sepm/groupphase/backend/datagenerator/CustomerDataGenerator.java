package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class CustomerDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerDataGenerator(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    public void generateOperators() {
        if (false) {
            LOGGER.debug("customers already generated");
        } else {
            Address address = addressRepository.save(new Address(-1L, "teststreet", 1000, "1a", 1, "1"));
            Customer customer = new Customer("test@gmail.com", "testpassw", "test", "test22", address, -1L, "06604233");
            customerRepository.save(customer);
            Address address2 = addressRepository.save(new Address(-2L, "teststreet", 1000, "1b", 2, "4"));
            Customer customer2 = new Customer("test2@gmail.com", "12345678", "test2", "test", address, -2L, "06604256");
            customerRepository.save(customer2);
            Address address3 = addressRepository.save(new Address(-3L, "otherstreet", 2000, "1b", 2, "4"));
            Customer customer3 = new Customer("user@hotmail.com", "910111213", "anothertest", "anothertest", address3, -3L, "06605256");
            Address address4 = addressRepository.save(new Address(-4L, "otherstreet", 2000, "1c", 4, "4"));
            Address address5 = addressRepository.save(new Address(-5L, "street", 2000, "1a", 5, "5"));
            Address address6 = addressRepository.save(new Address(-6L, "mainstreet", 2000, "10b", 6, "6"));
            Address address7 = addressRepository.save(new Address(-7L, "otherstreet", 2000, "12b", 1, "7"));
            Address address8 = addressRepository.save(new Address(-8L, "teststreet", 2000, "102b", 0, "11"));
            Address address9 = addressRepository.save(new Address(-9L, "street", 2000, "16b", 8, "25"));
            Address address10 = addressRepository.save(new Address(-10L, "mainstreet", 2000, "25b", 9, "40"));
            Address address11 = addressRepository.save(new Address(-11L, "street2", 2000, "67b", 10, "4"));
            Address address12 = addressRepository.save(new Address(-12L, "street2", 2000, "54b", 11, "2"));
            Address address13 = addressRepository.save(new Address(-13L, "street3", 2000, "99b", 13, "1"));
            Address address14 = addressRepository.save(new Address(-14L, "otherstreet", 2000, "11b", 14, "6"));
            Address address15 = addressRepository.save(new Address(-15L, "street", 2000, "1a", 15, "9"));
            Address address16 = addressRepository.save(new Address(-16L, "teststreet", 2000, "10a", 16, "10"));
            customerRepository.save(customer3);
            customerRepository.save(new Customer("test19@hotmail.com", "910141213", "nametest", "nametest", address4, -4L, "16605256"));
            customerRepository.save(new Customer("test2@hotmail.com", "240141213", "name", "name", address3, -5L, "16615256"));
            customerRepository.save(new Customer("test3@hotmail.com", "9101241213", "anothername", "anothername", address, -6L, "1662356"));
            customerRepository.save(new Customer("test4@gmail.com", "910125213", "test2", "test2", address2, -7L, "166034256"));
            customerRepository.save(new Customer("test5@gmx.com", "91011231213", "test3", "test3", address5, -8L, "166023456"));
            customerRepository.save(new Customer("test100@gmx.com", "91012441213", "test4", "test4", address6, -9L, "1660554356"));
            customerRepository.save(new Customer("test99@aon.at", "9101152213", "test5", "test5", address7, -10L, "166058756"));
            customerRepository.save(new Customer("test9@hotmail.com", "910141253", "test6", "test6", address8, -11L, "1660528906"));
            customerRepository.save(new Customer("test10@gmail.com", "910136213", "test7", "test7", address9, -12L, "1660525676"));
            customerRepository.save(new Customer("testmail@gmail.com", "910361213", "test8", "test8", address10, -13L, "166051256"));
            customerRepository.save(new Customer("mail@hotmail.com", "9135641213", "test9", "test9", address11, -14L, "1660545256"));
            customerRepository.save(new Customer("anothermail@hotmail.com", "91356141213", "test10", "test10", address12, -15L, "1660522346"));
            customerRepository.save(new Customer("mailtest@gmx.at", "910141236", "test11", "test11", address13, -16L, "166052356"));
            customerRepository.save(new Customer("test15@gmail.com", "9101356213", "test12", "test12", address14, -17L, "1660522346"));
            customerRepository.save(new Customer("tester@hotmail.com", "9103441213", "test13", "test13", address15, -18L, "1660523456"));
            customerRepository.save(new Customer("test22@gmail.com", "9101412313", "test14", "test14", address16, -19L, "1660526456"));
            customerRepository.save(new Customer("mailmail@hotmail.mail", "9101123213", "test15", "test15", address, -20L, "16605257656"));
            customerRepository.save(new Customer("bonuseventus@gmail.com", "953141213", "test16", "test16", address2, -21L, "166047256"));
            customerRepository.save(new Customer("hotmail@hotmail.com", "91168941213", "test17", "test17", address3, -22L, "1660527456"));
            customerRepository.save(new Customer("gmail@gmail.com", "910196213", "test18", "test18", address4, -23L, "1660574556"));
        }
    }
}
