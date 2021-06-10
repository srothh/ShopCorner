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
     * Saves the specified operator.
     *
     * @param operator to be saved
     * @return the operator that has just been saved.
     * @throws RuntimeException if the operator already exists
     */
    Operator save(Operator operator);

    /**
     * Changes Permission of operater with id.
     *
     * @param id          of operator that should be changed
     * @param permissions that operator should get
     * @throws NotFoundException when no operator with the id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    void changePermissions(Long id, Permissions permissions);


    /**
     * Deletes operator with id.
     *
     * @param id of operator that should be deleted
     * @throws NotFoundException when no operator with the given id is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    void delete(Long id);


    /**
     * Updates the specified operator.
     *
     * @param operator to be updated
     * @return the operator that has just been updated.
     * @throws NotFoundException if no matching operator is found in the database
     * @throws RuntimeException  if the updated operator account already exists
     */
    Operator update(Operator operator);

    /**
     * returns amount of employees.
     *
     * @return count of Employees.
     * @throws RuntimeException upon encountering errors with the database
     */
    Long getEmployeeCount();

    /**
     * returns amount of Admins.
     *
     * @return count of Admins.
     * @throws RuntimeException upon encountering errors with the database
     */
    Long getAdminCount();

    /**
     * Deletes all operators from the repository.
     * Solely to be used for testing purposes.
     *
     * @throws RuntimeException upon encountering errors with the database
     */
    void deleteAll();
}



