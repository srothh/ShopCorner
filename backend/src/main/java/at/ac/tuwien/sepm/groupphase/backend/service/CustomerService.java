package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;

public interface CustomerService {
    Customer registerNewCustomer(Customer customer);
}
