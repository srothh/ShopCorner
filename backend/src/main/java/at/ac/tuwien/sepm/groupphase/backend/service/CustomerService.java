package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * A service class handling customers.
 */
public interface CustomerService extends UserDetailsService {
    /**
     * Find a user in the context of Spring Security based on the login name.
     *
     * @param loginName the login name
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     * @throws RuntimeException          upon encountering errors with the database
     */
    @Override
    UserDetails loadUserByUsername(String loginName);

    /**
     * Find a customer based on the login name.
     *
     * @param loginName the login name
     * @return a customer
     * @throws NotFoundException when no customer with the id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    Customer findCustomerByLoginName(String loginName);

    /**
     * Registers a new customer and persists its entity in the database.
     *
     * @param customer The customer entity to save
     * @return The customer entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Customer registerNewCustomer(Customer customer);

    /**
     * Retrieves a page customers from the database.
     *
     * @param page      The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @return A page containing the customers retrieved
     */
    Page<Customer> getAllCustomers(int page, int pageCount);

    /**
     * Assigns an address to a customer.
     *
     * @param customer  The customer to assign the address to
     * @param addressId The id of the address to assign to the customer
     * @throws NotFoundException when no address with the id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    void assignAddressToCustomer(Customer customer, Long addressId);

    /**
     * Returns amount of customers in the database.
     *
     * @return The amount of customers in the database
     */
    long getCustomerCount();

}
