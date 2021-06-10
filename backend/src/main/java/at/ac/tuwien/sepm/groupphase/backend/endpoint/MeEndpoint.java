package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.invoke.MethodHandles;
import java.security.Principal;

/**
 * The MeEndpoint is responsible for handling requests to resources associated with a customer.
 * All requests need to include the access token in the header.
 */
@RestController
@RequestMapping(MeEndpoint.BASE_URL)
public class MeEndpoint {

    static final String BASE_URL = "api/v1/me";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @Autowired
    public MeEndpoint(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    @Secured({"ROLE_CUSTOMER"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve customer data", security = @SecurityRequirement(name = "apiKey"))
    public CustomerDto findCustomerByLoginName(Principal principal) {
        LOGGER.info("GET " + BASE_URL);
        Customer entity = customerService.findCustomerByLoginName(principal.getName());
        return customerMapper.customerToCustomerDto(entity);
    }

    @Secured({"ROLE_CUSTOMER"})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete customer", security = @SecurityRequirement(name = "apiKey"))
    public void deleteCustomerByUsername(Principal principal) {
        LOGGER.info("DELETE " + BASE_URL);
        customerService.deleteCustomerByLoginName(principal.getName());
    }
}
