package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerNewCustomer(Customer customer, Long addressId);

    List<Customer> getAllCustomers();
}
