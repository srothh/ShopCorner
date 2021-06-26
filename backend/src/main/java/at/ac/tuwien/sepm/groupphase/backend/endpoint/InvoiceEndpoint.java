package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceArchiveService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;


import java.lang.invoke.MethodHandles;
import java.util.Set;

import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceMapper invoiceMapper;
    private final InvoiceService invoiceService;
    private final InvoiceItemMapper invoiceItemMapper;
    private final PdfGeneratorService pdfGeneratorService;
    private final InvoiceArchiveService invoiceArchiveService;

    @Autowired
    public InvoiceEndpoint(InvoiceMapper invoiceMapper, InvoiceItemMapper invoiceItemMapper, InvoiceService invoiceService,
                           PdfGeneratorService pdfGeneratorService,
                            InvoiceArchiveService invoiceArchiveService) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceService = invoiceService;
        this.invoiceItemMapper = invoiceItemMapper;
        this.pdfGeneratorService = pdfGeneratorService;
        this.invoiceArchiveService = invoiceArchiveService;
    }

    /**
     * Get all information for specific invoice.
     *
     * @param id is the id of the invoice
     * @return DetailedInvoiceDto with all given information of the invoice
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information for specific invoice", security = @SecurityRequirement(name = "apiKey"))
    public DetailedInvoiceDto find(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/invoices/{}", id);
        return invoiceMapper.invoiceToDetailedInvoiceDto(invoiceService.findOneById(id));
    }

    /**
     * Get overviewing information for all invoices.
     *
     * @return List with all SimpleInvoices
     */

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all invoices", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<SimpleInvoiceDto> getAllInvoices(@Valid PaginationRequestDto paginationRequestDto, @RequestParam("invoiceType") InvoiceType invoiceType) {
        int page = paginationRequestDto.getPage();
        int pageCount = paginationRequestDto.getPageCount();
        LOGGER.info("GET api/v1/invoices?page={}&page_count={}&invoiceType={}", page, pageCount, invoiceType);
        Page<Invoice> invoicePage = invoiceService.findAll(page, pageCount, invoiceType);
        if (invoiceType == InvoiceType.operator) {
            return new PaginationDto<>(invoiceMapper.invoiceToSimpleInvoiceDto(invoicePage.getContent()), page, pageCount, invoicePage.getTotalPages(), invoiceService.getInvoiceCount());
        } else if (invoiceType == InvoiceType.customer) {
            return new PaginationDto<>(invoiceMapper.invoiceToSimpleInvoiceDto(invoicePage.getContent()), page, pageCount, invoicePage.getTotalPages(), invoiceService.getCustomerInvoiceCount());
        }
        return new PaginationDto<>(invoiceMapper.invoiceToSimpleInvoiceDto(invoicePage.getContent()), page, pageCount, invoicePage.getTotalPages(), invoiceService.getCanceledInvoiceCount());
    }


    /**
     * Creates a database entry and generates a pdf.
     *
     * @param invoiceDto which should be saved in the database
     * @return ResponseEntity with the generated pdf
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/pdf")
    @Operation(summary = "create new invoice", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<byte[]> createInvoiceAsPdf(@Valid @RequestBody DetailedInvoiceDto invoiceDto) {
        LOGGER.info("POST /api/v1/invoices/ {}", invoiceDto);
        Invoice invoice = invoiceMapper.simpleInvoiceDtoToInvoice(invoiceDto);
        Set<InvoiceItem> items = invoiceItemMapper.dtoToEntity(invoiceDto.getItems());
        invoice.setItems(items);
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        final byte[] contents = this.pdfGeneratorService.createPdfInvoiceOperator(invoiceService.findOneById(createdInvoice.getId()));
        return new ResponseEntity<>(contents, this.generateHeader(), HttpStatus.CREATED);
    }

    /**
     * Set an invoice to canceled in the database.
     *
     * @param invoiceId id of the invoice which should be updated in the database
     * @return DetailedInvoiceDto with the updated invoice
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}")
    @Operation(summary = "create new invoice", security = @SecurityRequirement(name = "apiKey"))
    public DetailedInvoiceDto resetInvoiceCanceled(@Valid @RequestBody DetailedInvoiceDto invoiceDto, @PathVariable("id") Long invoiceId) {
        LOGGER.info("PATCH /api/v1/invoices/{}: {}", invoiceId, invoiceDto);
        if (!invoiceDto.getId().equals(invoiceId)) {
            throw new ServiceException("Bad Request, invoiceId is not valid");
        }
        Invoice canceledInvoice = this.invoiceService.setInvoiceCanceled(this.invoiceService.findOneById(invoiceId));
        return this.invoiceMapper.invoiceToDetailedInvoiceDto(canceledInvoice);
    }

    /**
     * Finds an invoice and generates a PDF from it.
     *
     * @param id id of the invoice
     * @return ResponseEntity with the generated pdf
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/pdf", produces = "application/pdf")
    @Operation(summary = "Retrieve new invoice as pdf", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<byte[]> getInvoiceAsPdf(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/invoices/{}/pdf", id);
        Invoice invoice = invoiceService.findOneById(id);
        byte[] contents = null;

        if (invoice.getInvoiceType() == InvoiceType.operator) {
            contents = this.pdfGeneratorService.createPdfInvoiceOperator(invoice);
        } else if (invoice.getInvoiceType() == InvoiceType.customer) {
            contents = this.pdfGeneratorService.createPdfInvoiceCustomerFromInvoice(invoice);
        } else if (invoice.getInvoiceType() == InvoiceType.canceled) {
            contents = this.pdfGeneratorService.createPdfCanceledInvoiceOperator(invoice);
        } else {
            return ResponseEntity.status(402).build();
        }

        return new ResponseEntity<>(contents, this.generateHeader(), HttpStatus.OK);
    }


    /**
     * generates response header for the create and get invoice as pdf request.
     *
     * @return header for the pdf response
     */
    private HttpHeaders generateHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }


}
