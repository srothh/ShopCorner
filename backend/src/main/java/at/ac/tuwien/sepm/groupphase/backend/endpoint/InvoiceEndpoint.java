package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceMapper invoiceMapper;
    private final InvoiceService invoiceService;
    private final InvoiceItemService invoiceItemService;
    private final InvoiceItemMapper invoiceItemMapper;

    @Autowired
    public InvoiceEndpoint(InvoiceMapper invoiceMapper, InvoiceItemMapper invoiceItemMapper, InvoiceService invoiceService, InvoiceItemService invoiceItemService) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceService = invoiceService;
        this.invoiceItemMapper = invoiceItemMapper;
        this.invoiceItemService = invoiceItemService;
    }

    //@Operation(summary = "Get information for specific invoice", security = @SecurityRequirement(name = "apiKey"))
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information for specific invoice")
    public DetailedInvoiceDto find(@PathVariable Long id) {
        LOGGER.info("GET /invoice/{}", id);
        return invoiceMapper.invoiceToDetailedInvoiceDto(invoiceService.findOneById(id));
    }
    //@Operation(summary = "Get information for specific invoice", security = @SecurityRequirement(name = "apiKey"))

    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    @Operation(summary = "Get information for specific invoice")
    public List<SimpleInvoiceDto> findAll() {
        LOGGER.info("GET /invoices");
        return invoiceMapper.invoiceToSimpleInvoiceDto(invoiceService.findAllInvoices());
    }


    //@Operation(summary = "create new invoice", security = @SecurityRequirement(name = "apiKey"))
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    @Operation(summary = "create new invoice")
    public SimpleInvoiceDto createInvoice(@RequestBody DetailedInvoiceDto invoiceDto) {
        LOGGER.info("GET /invoices");
        Invoice invoice = invoiceMapper.simpleInvoiceDtoToInvoice(invoiceDto);

        Set<InvoiceItem> items = invoiceItemMapper.dtoToEntity(invoiceDto.getItems());
        Invoice createdInvoice = invoiceService.creatInvoice(invoice);
        SimpleInvoiceDto newInvoice = invoiceMapper.invoiceToSimpleInvoiceDto(createdInvoice);
        for (InvoiceItem item : items) {
            item.setInvoice(createdInvoice);
            System.out.println(item);
        }
        invoiceItemService.creatInvoiceItem(items);
        return newInvoice;

    }


}