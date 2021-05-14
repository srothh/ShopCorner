package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CustomerServiceImpl(PasswordEncoder passwordEncoder, Validator validator, CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Customer registerNewCustomer(Customer customer, Long addressId) {
        LOGGER.trace("registerNewCustomer({})", customer);
        validator.validateCustomerRegistration(customer);
        assignAddressToCustomer(customer, addressId);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer temp = customerRepository.save(customer);
        temp.setPassword(null);
        return temp;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customer.setPassword(null);
        }

        return customers;
    }

    @Transactional
    public void assignAddressToCustomer(Customer customer, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Could not find address!"));
        customer.setAddress(address);
    }

}
