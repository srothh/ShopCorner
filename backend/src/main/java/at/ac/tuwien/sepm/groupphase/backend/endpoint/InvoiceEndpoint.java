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
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;


import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getinvoicepdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public String getInvoiceAsPdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.findOneById(id);
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("htmlToPdfTemplate/");
        File result = null;

        String fileOutput = String.format("invoices/invoice_%s_%s.pdf", invoice.getDate().format(dateFormatter), invoice.getId());

        try {
            String html = new String(Files.readAllBytes(Paths.get("htmlToPdfTemplate/invoiceTemplate_v1.html")));

            final Document document = Jsoup.parse(html);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.body().select(".invoice-date").html(invoice.getDate().format(dateFormatter));
            document.body().select(".invoice-number").html(invoice.getId() + "");
            Element table = document.body().select(".article").first();
            String tableAsString = "<table class=\"center\" style=\"width: 100%;\"><tr><td class=\"center underline\"><h4>Bestellte Artikel</h4></td></tr><tr class=\"border\"><td class=\"center product\"><span>Produkt</span></td><td class=\"center price\"><span>Preis</span></td><td class=\"center quantity\"><span>Anzahl</span></td><td class=\"center tax\"><span>Steuer</span></td><td class=\"center amount\"><span>Betrag</span></td></tr>";
            double total = 0;
            double subtotal = 0;
            double tax = 0;

            for (InvoiceItem i : invoice.getItems()) {
                Product p = i.getProduct();
                TaxRate t = p.getTaxRate();
                double subtotalPerProduct = p.getPrice() * i.getNumberOfItems();
                double taxPerProduct = p.getPrice() * i.getNumberOfItems() * ((t.getPercentage() / 100));
                double totalPerProduct = subtotalPerProduct + taxPerProduct;
                subtotal = subtotal + subtotalPerProduct;
                tax = tax + taxPerProduct;
                total = total + totalPerProduct;
                tableAsString = tableAsString + String.format("<tr class=\"border\"><td class=\"center product\"><span>%s</span></td><td class=\"center price\"><span>%s</span></td><td class=\"center quantity\"><span>%s</span></td><td class=\"center tax\"><span>%s</span></td><td class=\"center amount\"><span>%s</span></td></tr>",
                    p.getName(), p.getPrice(), i.getNumberOfItems(), t.getPercentage(), totalPerProduct + "");
            }
            tableAsString = tableAsString + "</table>";
            table.html(tableAsString);



            HtmlConverter.convertToPdf(document.html(), new FileOutputStream(fileOutput), properties);
            //result = new File(String.format("invoices/invoice_%s_%s.pdf",  invoice.getDate().format(dateFormatter),invoice.getId()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }

        /*ITextRenderer renderer = new ITextRenderer();
        ByteArrayOutputStream boas = null;
        try {

            String inputFile = "htmlToPdfTemplate/invoiceTemplate_v1.html";
            String outputFile = "invoices/" + filenameDate.format(new Date()) + ".pdf";

            String html = new String(Files.readAllBytes(Paths.get(inputFile)));
            final Document document = Jsoup.parse(html);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.body().select(".DOC_GENERATED_DATE").html(readableDate.format(new Date()));

            renderer.setDocumentFromString(document.html());
            renderer.layout();

            try (OutputStream os = Files.newOutputStream(Paths.get(outputFile))) {
                renderer.createPDF(os);
                os.close();

                PdfReader reader = new PdfReader(outputFile);
                boas = new ByteArrayOutputStream();
                PdfStamper stamper = new PdfStamper(reader, boas);
                stamper.setPageAction(PdfWriter.PAGE_OPEN, new PdfAction(PdfAction.PRINTDIALOG), 1);
                stamper.close();
            } catch (DocumentException ex) {
                //Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   catch (IOException ex) {
            //Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(boas.toByteArray(), headers, HttpStatus.OK);*/
        return "test";
    }


}