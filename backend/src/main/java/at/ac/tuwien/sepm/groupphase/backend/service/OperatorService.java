package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
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
     * .
     * Find an operator in the context of Spring Security based on the login name
     *
     * @param loginName the login name
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException;

    /**
     * Find an operator based on the login name.
     *
     * @param loginName the login name
     * @return an operator
     * @throws NotFoundException when no operator with the id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    Operator findOperatorByLoginName(String loginName);

    /**
     * Returns page with all needed Operators.
     *
     * @param page        which should be returned
     * @param permissions of Operators which should be returned
     * @param pageCount   amount of operators per page
     * @return Page with all Operators with right permission
     */
    Page<Operator> findAll(int page, int pageCount, Permissions permissions);

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



    /**
     * Updates the specified operator.
     *
     * @param operator to be updated
     * @return the operator that has just been updated.
     */
    Operator update(Operator operator);

}



