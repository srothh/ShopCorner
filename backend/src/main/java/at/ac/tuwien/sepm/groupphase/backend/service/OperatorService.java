package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Service class for operators.
 */
public interface OperatorService extends UserDetailsService {
    /**
     * Find a user in the context of Spring Security based on the email address.
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Returns page with all needed Operators.
     *
     * @param page which should be returned
     * @param permissions of Operators which should be returned
     * @return Page with all Operators with right permission
     */
    Page<Operator> findAll(int page, Permissions permissions);

    /**
     * Find all operators.
     *
     * @return a list of operators
     */
    List<Operator> findAll();

    /**
     * returns amount of admins and employees.
     *
     * @return an Array where [0] is amount of Admins and [1] is amount of Employees
     */
    int[] getCollectionSize();

    /**
     * Saves the specified operator.
     *
     * @param operator to be saved
     * @return the operator that has just been saved.
     */
    Operator save(Operator operator);
}