package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CustomerServiceImpl(PasswordEncoder passwordEncoder, Validator validator, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerNewCustomer(Customer customer) {
        LOGGER.trace("registerNewCustomer({})", customer);
        validator.validateCustomerRegistration(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.registerNewCustomer(customer.getId(), customer.getName(), customer.getLoginName(), customer.getPassword(), customer.getEmail(), customer.getAddress(), customer.getPhoneNumber());
    }
}
