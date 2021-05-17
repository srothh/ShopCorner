package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/** A service class handling customers.
 */
public interface CustomerService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String loginName);

    Customer findCustomerByLoginName(String loginName);

    Customer registerNewCustomer(Customer customer);

    List<Customer> getAllCustomers();
}
