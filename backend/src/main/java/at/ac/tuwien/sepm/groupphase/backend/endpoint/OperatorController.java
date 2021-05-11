package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(OperatorController.BASE_URL)
public class OperatorController {

    static final String BASE_URL = "/operators";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OperatorService operatorService;
    private final OperatorMapper operatorMapper;

    @Autowired
    public OperatorController(OperatorService operatorService, OperatorMapper operatorMapper){
        this.operatorService = operatorService;
        this.operatorMapper = operatorMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public OperatorDto registerOperator(@RequestBody OperatorDto newOperator) {
        LOGGER.info("POST " + BASE_URL + "/register body: {}", newOperator);

        try {
            Operator operator = operatorMapper.dtoToEntity(newOperator);
            operatorService.save(operator);
            return operatorMapper.entityToDto(operator);
        }
        catch(ValidationException e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }


}
