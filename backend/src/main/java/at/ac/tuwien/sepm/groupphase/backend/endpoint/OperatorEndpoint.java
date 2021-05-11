package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OverviewOperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.OverviewOperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(OperatorEndpoint.BASE_URL)
public class OperatorEndpoint {

    static final String BASE_URL = "/operators";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OverviewOperatorMapper overviewOperatorMapper;
    private final OverviewOperatorService overviewOperatorService;

    @Autowired
    public OperatorEndpoint(OverviewOperatorMapper overviewOperatorMapper, OverviewOperatorService overviewOperatorService) {
        this.overviewOperatorMapper = overviewOperatorMapper;
        this.overviewOperatorService = overviewOperatorService;
    }

    @PermitAll
    @GetMapping
    @Operation(summary = "Get list of operators", security = @SecurityRequirement(name = "apiKey"))
    public List<OverviewOperatorDto> getAll() {
        LOGGER.info("GET /operators");
        return overviewOperatorMapper.overviewOperatorToDto(overviewOperatorService.getAll());
    }

}
