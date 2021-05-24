package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface OperatorService extends UserDetailsService {

    /** .
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
     */
    Operator save(Operator operator);

}
