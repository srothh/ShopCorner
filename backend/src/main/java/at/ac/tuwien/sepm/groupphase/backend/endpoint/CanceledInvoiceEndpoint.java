package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CanceledInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CanceledInvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.service.CanceledInvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("api/v1/canceledInvoices")
public class CanceledInvoiceEndpoint {


    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InvoiceService invoiceService;
    private final CanceledInvoiceService canceledInvoiceService;
    private final CanceledInvoiceMapper canceledInvoiceMapper;
    private final PdfGeneratorService pdfGeneratorService;

    @Autowired
    public CanceledInvoiceEndpoint(InvoiceService invoiceService, CanceledInvoiceService canceledInvoiceService, CanceledInvoiceMapper canceledInvoiceMapper, PdfGeneratorService pdfGeneratorService) {
        this.invoiceService = invoiceService;
        this.canceledInvoiceService = canceledInvoiceService;
        this.canceledInvoiceMapper = canceledInvoiceMapper;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all canceled invoices", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<CanceledInvoiceDto> getAllCanceledInvoices(@Valid PaginationRequestDto paginationRequestDto) {
        int page = paginationRequestDto.getPage();
        int pageCount = paginationRequestDto.getPageCount();
        LOGGER.info("GET api/v1/canceledInvoices?page={}&page_count={}", page, pageCount);

        Page<CanceledInvoice> invoicePage = this.canceledInvoiceService.findAll(page, pageCount);

        return new PaginationDto<>(canceledInvoiceMapper.canceledInvoiceToCanceledInvoiceDto(invoicePage.getContent()), page, pageCount, invoicePage.getTotalPages(), invoiceService.getInvoiceCount());
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create canceled Invoice", security = @SecurityRequirement(name = "apiKey"))
    public CanceledInvoiceDto createCanceledInvoice(@Valid @RequestBody CanceledInvoiceDto canceled) {
        CanceledInvoice canceledInvoice = this.canceledInvoiceMapper.canceledInvoiceDtoToCanceledInvoice(canceled);
        CanceledInvoice createdCanceledInvoice = this.canceledInvoiceService.createCanceledInvoice(canceledInvoice);
        return this.canceledInvoiceMapper.canceledInvoiceToCanceledInvoiceDto(createdCanceledInvoice);
    }

    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    @PostMapping("application/pdf")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create canceled Invoice", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<byte[]> createCanceledInvoicePdf(@Valid @RequestBody CanceledInvoiceDto canceled) {
        CanceledInvoice canceledInvoice = this.canceledInvoiceMapper.canceledInvoiceDtoToCanceledInvoice(canceled);
        CanceledInvoice createdCanceledInvoice = this.canceledInvoiceService.createCanceledInvoice(canceledInvoice);
        byte[] content = this.pdfGeneratorService.createPdfCanceledInvoiceOperator(createdCanceledInvoice);
        return new ResponseEntity<>(content, this.generateHeader(), HttpStatus.CREATED);

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
