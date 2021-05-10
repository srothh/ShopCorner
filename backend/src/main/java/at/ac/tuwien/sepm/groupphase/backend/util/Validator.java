package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void validateCustomerRegistration(Customer customer) {
        LOGGER.trace("validateCustomerRegistration({})", customer);
        if (customer.getName() == null || customer.getName().matches("\\s*")) {
            throw new ValidationException("Name is mandatory");
        }
        if (customer.getName().length() > 255) {
            throw new ValidationException("Name exceeds maximum length of 255 Characters");
        }
        if (customer.getLoginName() == null || customer.getLoginName().matches("\\s*")) {
            throw new ValidationException("Login name is mandatory");
        }
        if (customer.getLoginName().length() > 128) {
            throw new ValidationException("Login name exceeds maximum length of 255 Characters");
        }
        if (customer.getPassword() == null || customer.getPassword().matches("\\s*")) {
            throw new ValidationException("Password is mandatory");
        }
        if (customer.getPassword().length() > 128) {
            throw new ValidationException("Password exceeds maximum length of 128 Characters");
        }
        if (customer.getEmail() == null) {
            throw new ValidationException("Email is mandatory");
        }
        if (customer.getEmail().length() > 255) {
            throw new ValidationException("Email exceeds maximum length of 255 characters");
        }
        if (customer.getAddress() == null) {
            throw new ValidationException("Address is mandatory");
        }
        //TODO: check whether address exists
    }
}
