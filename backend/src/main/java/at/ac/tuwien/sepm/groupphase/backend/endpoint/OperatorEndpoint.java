package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(OperatorEndpoint.BASE_URL)
public class OperatorEndpoint {

    static final String BASE_URL = "api/v1/operators";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OperatorMapper operatorMapper;
    private final OperatorService operatorService;

    @Autowired
    public OperatorEndpoint(OperatorMapper operatorMapper, OperatorService operatorService) {
        this.operatorMapper = operatorMapper;
        this.operatorService = operatorService;
    }

    @PermitAll
    @GetMapping
    @Operation(summary = "Get list of operators", security = @SecurityRequirement(name = "apiKey"))
    public List<OverviewOperatorDto> getAll() {
        LOGGER.info("GET /operators");
        return operatorMapper.operatorToOverviewOperatorDto(operatorService.findAll());
    }

}
