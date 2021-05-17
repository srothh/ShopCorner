package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;

import java.util.List;

/** A service class handling customers.
 */
public interface CustomerService {
    Customer registerNewCustomer(Customer customer);

    List<Customer> getAllCustomers();
}
