package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import at.ac.tuwien.sepm.groupphase.backend.util.PdfGenerator;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;


import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
    public DetailedInvoiceDto find(@PathVariable Long id) throws IOException {
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

    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getinvoicepdf/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> getInvoiceAsPdf(@PathVariable Long id) throws IOException {
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


    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createinvoicepdf", produces = "application/pdf")
    @Operation(summary = "create new invoice")
    public ResponseEntity<byte[]> createInvoiceAsPdf(@RequestBody DetailedInvoiceDto invoiceDto) throws IOException {
        LOGGER.info("Create /invoices/createinvoicepdf {}", invoiceDto);
        return this.getInvoiceAsPdf(this.createInvoice(invoiceDto).getId());

    }

}