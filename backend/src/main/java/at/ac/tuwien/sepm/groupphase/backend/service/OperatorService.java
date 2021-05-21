package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface OperatorService extends UserDetailsService {

    /** .
     * Find a user in the context of Spring Security based on the email address
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

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


    /**
     * Updates the specified operator.
     *
     * @param operator to be updated
     * @return the operator that has just been updated.
     */
    Operator update(Operator operator);

}
