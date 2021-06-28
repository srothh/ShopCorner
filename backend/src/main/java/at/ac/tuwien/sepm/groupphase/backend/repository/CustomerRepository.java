package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for customers.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a Customer based on the login name.
     *
     * @return a customer with the login name
     */
    Customer findByLoginName(String loginName);

    /**
     * Finds a Customer based on the email.
     *
     * @return a customer with the email
     */
    Customer findByEmail(String email);

}
