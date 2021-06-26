package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

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
     * @throws RuntimeException upon encountering errors with the database
     */
    @Override
    UserDetails loadUserByUsername(String loginName);

    /**
     * Find a customer based on the login name.
     *
     * @param loginName the login name
     * @return a customer
     * @throws NotFoundException when no customer with the id is found
     * @throws RuntimeException upon encountering errors with the database
     */
    Customer findCustomerByLoginName(String loginName);

    /**
     * Deletes a customer based on the login name.
     *
     * @param loginName the login name
     * @throws NotFoundException when no customer with the id is found
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteCustomerByLoginName(String loginName);

    /**
     * Registers a new customer and persists its entity in the database.
     *
     * @param customer The customer entity to save
     * @return The customer entity added to the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Customer registerNewCustomer(Customer customer);

    /**
     * Updates the specified customer.
     *
     * @param customer to be updated
     * @return the customer that has just been updated.
     * @throws NotFoundException if no matching customer is found in the database
     * @throws RuntimeException  if the updated customer account already exists
     */
    Customer update(Customer customer);

    /**
     * Updates the password of the specified customer.
     *
     * @param id of the customer whose password is to be updated
     * @param oldPassword the password to be updated
     * @param newPassword the new password
     * @throws NotFoundException if no matching customer is found in the database
     * @throws RuntimeException  if the password could not be updated
     */
    void updatePassword(Long id, String oldPassword, String newPassword);


    /**
     * Retrieves a PaginationDto containing a Page of customers from the database.
     *
     * @param page The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @return A PaginationDto containing data about the retrieved items
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Customer> getAllCustomers(int page, int pageCount);

    /**
     * Assigns an address to a customer.
     *
     * @param customer  The customer to assign the address to
     * @param addressId The id of the address to assign to the customer
     * @throws NotFoundException when no address with the id is found
     * @throws RuntimeException upon encountering errors with the database
     */
    void assignAddressToCustomer(Customer customer, Long addressId);

    /**
     * Returns amount of customers in the database.
     *
     * @return The amount of customers in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    long getCustomerCount();

    /**
     * Returns all customers from the database.
     *
     * @return A list containing all the customers in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    List<Customer> findAll();

    /**
     * Returns a specific customer from the database.
     *
     * @param id the id of the customer
     * @return the specific customer
     * @throws NotFoundException is thrown if the specified customer does not exists
     * @throws RuntimeException upon encountering errors with the database
     */
    Customer findCustomerById(Long id);

    Long getCountByCategory(Pageable page, Long category);

}
