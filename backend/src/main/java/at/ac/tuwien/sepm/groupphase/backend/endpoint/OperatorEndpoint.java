package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(OperatorEndpoint.BASE_URL)
public class OperatorEndpoint {

    static final String BASE_URL = "/api/v1/operators";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OperatorService operatorService;
    private final OperatorMapper operatorMapper;

    @Autowired
    public OperatorEndpoint(OperatorService operatorService, OperatorMapper operatorMapper) {
        this.operatorService = operatorService;
        this.operatorMapper = operatorMapper;
    }

    @PermitAll //TODO change to @Secured("ROLE_ADMIN")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new operator account", security = @SecurityRequirement(name = "apiKey"))
    public OperatorDto registerOperator(@Valid @RequestBody OperatorDto newOperator) {
        LOGGER.info("POST " + BASE_URL + "/register body: {}", newOperator);

        Operator operator = operatorMapper.dtoToEntity(newOperator);
        OperatorDto result = operatorMapper.entityToDto(operatorService.save(operator));
        result.setPassword(null);
        return result;

    }

    @PermitAll
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit an existing operator account", security = @SecurityRequirement(name = "apiKey"))
    public OperatorDto editOperator(@Valid @PathVariable("id") Long id, @RequestBody OperatorDto operatorDto) {
        LOGGER.info("PUT " + BASE_URL + "/{}", id);

        Operator operator = operatorMapper.dtoToEntity(operatorDto);
        OperatorDto result = operatorMapper.entityToDto(operatorService.update(operator));
        result.setPassword(null);
        return result;

    }


}
