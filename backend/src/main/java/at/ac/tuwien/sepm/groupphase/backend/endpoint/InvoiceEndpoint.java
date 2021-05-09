package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;


@RestController
@RequestMapping("/invoice")
public class InvoiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceMapper invoiceMapper;
    private final InvoiceService invoiceService;


    @Autowired
    public InvoiceEndpoint(InvoiceMapper invoiceMapper, InvoiceService invoiceService) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceService=invoiceService;
    }

    @PermitAll
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information for specific invoice", security = @SecurityRequirement(name = "apiKey"))
    public DetailedInvoiceDto find(@PathVariable Long id) {
        LOGGER.info("GET /invoice/{}", id);
        return invoiceMapper.invoiceToDetailedInvoiceDto(invoiceService.findOne(id));
    }

    @PermitAll
    @GetMapping(value = "")
    @Operation(summary = "Get information for specific invoice", security = @SecurityRequirement(name = "apiKey"))
    public List<SimpleInvoiceDto> findAll() {
        LOGGER.info("GET /invoices");
        return invoiceMapper.invoiceToSimpleInvoiceDto(invoiceService.findAll());
    }
}
