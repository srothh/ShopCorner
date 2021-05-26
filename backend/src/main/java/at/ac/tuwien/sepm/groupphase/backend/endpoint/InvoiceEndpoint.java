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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import at.ac.tuwien.sepm.groupphase.backend.util.PdfGenerator;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


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

    /**
     * Get all information for specific invoice.
     *
     * @param id is the id of the invoice
     * @return DetailedInvoiceDto with all given information of the invoice
     */
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information for specific invoice")
    public DetailedInvoiceDto find(@PathVariable Long id) {
        LOGGER.info("GET /invoice/{}", id);
        return invoiceMapper.invoiceToDetailedInvoiceDto(invoiceService.findOneById(id));

    }

    /**
     * Get overviewing information for all invoices.
     *
     * @return List with all SimpleInvoices
     */
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    @Operation(summary = "Get overviewing information for all invoices")
    public List<SimpleInvoiceDto> findAll() {
        LOGGER.info("GET /invoices");
        return invoiceMapper.invoiceToSimpleInvoiceDto(invoiceService.findAllInvoices());
    }

    /**
     * Create new invoice.
     *
     * @param invoiceDto which should be saved in the database
     * @return List with all SimpleInvoices
     */
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "",produces = { "application/json"})
    @Operation(summary = "create new invoice")
    public SimpleInvoiceDto createInvoice(@Valid @RequestBody DetailedInvoiceDto invoiceDto) {
        LOGGER.info("Create /invoices {}", invoiceDto);
        Invoice invoice = invoiceMapper.simpleInvoiceDtoToInvoice(invoiceDto);

        Set<InvoiceItem> items = invoiceItemMapper.dtoToEntity(invoiceDto.getItems());
        Invoice createdInvoice = invoiceService.creatInvoice(invoice);
        SimpleInvoiceDto newInvoice = invoiceMapper.invoiceToSimpleInvoiceDto(createdInvoice);
        for (InvoiceItem item : items) {
            item.setInvoice(createdInvoice);
        }
        invoiceItemService.creatInvoiceItem(items);
        return newInvoice;
    }

    /**
     * Creates a database entry and generates a pdf.
     *
     * @param invoiceDto which should be saved in the database
     * @return ResponseEntity with the generated pdf
     */
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createinvoicepdf", produces = "application/pdf")
    @Operation(summary = "create new invoice")
    public ResponseEntity<byte[]> createInvoiceAsPdf(@Valid @RequestBody DetailedInvoiceDto invoiceDto) {
        LOGGER.info("Create /invoices/createinvoicepdf {}", invoiceDto);
        return this.getInvoiceAsPdf(this.createInvoice(invoiceDto).getId());

    }



    /**
     * Finds an invoice and generates a PDF from it.
     *
     * @param id id of the invoice
     * @return ResponseEntity with the generated pdf
     */
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getinvoicepdf/{id}", produces = "application/pdf")
    public ResponseEntity<byte[]> getInvoiceAsPdf(@PathVariable Long id) {
        LOGGER.info("Get /invoices/getinvoicepdf/{}", id);
        Invoice invoice = invoiceService.findOneById(id);
        PdfGenerator pdf = new PdfGenerator();
        final byte[] contents = pdf.generatePdfAsByteArray(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
    }


}