package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
public class CustomerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    @Autowired
    public CustomerEndpoint(CustomerMapper customerMapper, CustomerService customerService){
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Register a new customer account", security = @SecurityRequirement(name = "apiKey"))
    public CustomerRegistrationDto registerNewCustomer(@Valid @RequestBody CustomerRegistrationDto dto){
        LOGGER.info("POST {}",dto);
        return customerMapper.CustomerToCustomerDto(customerService.registerNewCustomer(customerMapper.CustomerDtoToCustomer(dto)));
    }
}
