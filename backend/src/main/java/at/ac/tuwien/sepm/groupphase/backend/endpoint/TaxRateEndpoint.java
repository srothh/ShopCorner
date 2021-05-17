package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaxRateDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TaxRateMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import at.ac.tuwien.sepm.groupphase.backend.service.TaxRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaxRateEndpoint {
    private static final String BASE_URL = "/api/v1/tax-rates";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TaxRateService taxRateService;
    private final TaxRateMapper taxRateMapper;

    @Autowired
    public TaxRateEndpoint(TaxRateService taxRateService, TaxRateMapper taxRateMapper) {
        this.taxRateService = taxRateService;
        this.taxRateMapper = taxRateMapper;
    }


    @PermitAll
    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all tax-rates that are currently saved in the database")
    public List<TaxRateDto> getAllTaxRates() {
        LOGGER.info("GET " + BASE_URL);
        return this.taxRateService.getAllTaxRates()
            .stream()
            .map(this.taxRateMapper::entityToDto)
            .collect(Collectors.toList());
    }
}
