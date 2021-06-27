package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CustomerRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AddressService;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Validator validator;

    @Autowired
    public CustomerServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository, AddressRepository addressRepository, AddressService addressService, Validator validator) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        LOGGER.trace("loadCustomerByUsername({})", loginName);
        try {
            Customer customer = this.findCustomerByLoginName(loginName);
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_CUSTOMER");
            return new User(customer.getLoginName(), customer.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public Customer findCustomerByLoginName(String loginName) {
        LOGGER.trace("findCustomerByLoginName({})", loginName);
        Customer customer = customerRepository.findByLoginName(loginName);
        if (customer != null) {
            return customer;
        }
        throw new NotFoundException(String.format("Kunde mit Username %s konnte nicht gefunden werden", loginName));
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'customers'"),
        @CacheEvict(value = "customerPages", allEntries = true)
    })
    @Override
    public void deleteCustomerByLoginName(String loginName) {
        LOGGER.trace("deleteCustomerByLoginName({})", loginName);
        Customer customer = customerRepository.findByLoginName(loginName);
        customerRepository.delete(customer);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'customers'"),
        @CacheEvict(value = "customerPages", allEntries = true)
    })
    @Override
    public Customer registerNewCustomer(Customer customer) {
        LOGGER.trace("registerNewCustomer({})", customer);
        validator.validateNewCustomer(customer, customerRepository);
        Address address = addressService.addNewAddress(customer.getAddress());
        assignAddressToCustomer(customer, address.getId());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'customers'"),
        @CacheEvict(value = "customerPages", allEntries = true)
    })
    @Override
    public Customer update(Customer customer) {
        LOGGER.trace("update({})", customer);
        validator.validateUpdatedCustomer(customer, customerRepository);
        Customer c = customerRepository.findById(customer.getId())
            .orElseThrow(() -> new NotFoundException(String.format("Kunde mit id %d konnte nicht gefunden werden", customer.getId())));

        if (!customer.getAddress().equals(c.getAddress())) {
            Address address = addressService.addNewAddress(customer.getAddress());
            assignAddressToCustomer(c, address.getId());
        }

        c.setEmail(customer.getEmail());
        c.setLoginName(customer.getLoginName());
        c.setName(customer.getName());
        c.setPhoneNumber(customer.getPhoneNumber());

        return customerRepository.save(c);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        LOGGER.trace("updatePassword({})", id);
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Kunde mit id %d konnte nicht gefunden werden", id)));

        if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepository.save(customer);
        } else {
            throw new ValidationException("Passwort konnte nicht upgedated werden");
        }
    }


    @Override
    @Cacheable(value = "customerPages")
    public Page<Customer> getAllCustomers(int page, int pageCount) {
        LOGGER.trace("getAllCustomers()");
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return customerRepository.findAll(returnPage);
    }

    @CacheEvict(value = "customerPages", allEntries = true)
    @Transactional
    public void assignAddressToCustomer(Customer customer, Long addressId) {
        LOGGER.trace("assignAddressToCustomer({},{})", customer, addressId);
        Address address = addressService.findAddressById(addressId);
        customer.setAddress(address);
    }

    @Cacheable(value = "counts", key = "'customers'")
    @Override
    public long getCustomerCount() {
        return customerRepository.count();
    }


    @Override
    public List<Customer> findAll() {
        LOGGER.trace("findAll()");
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(Long id) {
        LOGGER.trace("findAll()");
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Kunde konnte nicht gefunden werden"));
    }

    @Override
    public Long getCountByCategory(Pageable page, Long category) {
        return null;
    }

}
