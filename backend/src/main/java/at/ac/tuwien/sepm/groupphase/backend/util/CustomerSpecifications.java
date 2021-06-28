package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {

    public CustomerSpecifications() {
    }

    /**
     * returns specification that searches for given deleted status.
     *
     * @param deleted that need to be searched for
     * @return specification (includes root, criteriaQuery and criteriaBuilder)
     */
    public static Specification<Customer> hasDeleted(boolean deleted) {
        return (customer, cq, cb) -> cb.equal(customer.get("deleted"), deleted);
    }
}
