package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AddressService;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
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
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CustomerServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository, AddressRepository addressRepository, AddressService addressService) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.addressService = addressService;
    }

    /**
     * Registers a new customer and persists its entity in the database.
     *
     * @param customer  The customer entity to save
     * @return The customer entity added to the database
     * @throws RuntimeException upon errors with the database
     */
    @Transactional
    @Override
    public Customer registerNewCustomer(Customer customer) {
        LOGGER.trace("registerNewCustomer({})", customer);
        System.out.println(customer.getPassword());
        Address address = addressService.addNewAddress(customer.getAddress());
        assignAddressToCustomer(customer, address.getId());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of all found customers
     * @throws NotFoundException when no customers were found
     */
    @Override
    public List<Customer> getAllCustomers() {
        LOGGER.trace("getAllCustomers()");
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customer.setPassword(null);
        }

        return customers;
    }

    /**
     * Assigns an address to a customer.
     *
     * @param customer  The customer to assign the address to
     * @param addressId The id of the address to assign to the customer
     * @throws NotFoundException when no address with the id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    @Transactional
    public void assignAddressToCustomer(Customer customer, Long addressId) {
        LOGGER.trace("assignAddressToCustomer({},{})", customer, addressId);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Could not find address!"));
        customer.setAddress(address);
    }

}
