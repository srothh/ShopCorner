package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InvoiceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InvoiceEndpointTest implements TestData {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceItemMapper invoiceItemMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private final InvoiceItemKey invoiceItemKey = new InvoiceItemKey();
    private final InvoiceItem invoiceItem = new InvoiceItem();
    private final Invoice invoice = new Invoice();
    private final Product product = new Product();
    private final TaxRate taxRate = new TaxRate();

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        productRepository.deleteAll();
        taxRateRepository.deleteAll();
        // product
        product.setId(0L);
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_PRODUCT_DESCRIPTION);
        product.setPrice(TEST_PRODUCT_PRICE);
        product.setCategory(null);
        product.setTaxRate(null);

        // invoiceItemKey
        invoiceItemKey.setInvoiceId(invoice.getId());
        invoiceItemKey.setProductId(product.getId());

        // invoiceItem
        invoiceItem.setId(invoiceItemKey);
        invoiceItem.setInvoice(invoice);
        invoiceItem.setProduct(product);
        invoiceItem.setNumberOfItems(10);

        // invoiceItem to invoice
        Set<InvoiceItem> items = new HashSet<>();
        items.add(invoiceItem);

        invoice.setDate(LocalDateTime.now());
        invoice.setAmount(TEST_INVOICE_AMOUNT);
        invoice.setItems(items);

    }

    @Test
    public void givenAProductAndATaxRate_whenPost_thenInvoiceWithAllSetPropertiesPlusId() throws Exception {
        taxRateRepository.save(taxRate);
        productRepository.save(product);
        DetailedInvoiceDto detailedInvoiceDto = invoiceMapper.invoiceToDetailedInvoiceDto(invoice);
        detailedInvoiceDto.setItems(invoiceItemMapper.entityToDto(invoice.getItems()));

        String body = objectMapper.writeValueAsString(detailedInvoiceDto);

        MvcResult mvcResult = this.mockMvc.perform(post(INVOICE_BASE_URI + "/createinvoicepdf")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, response.getContentType());

        ResponseEntity  responseEntity = objectMapper.readValue(response.getContentAsByteArray(), ResponseEntity.class);
        assertNotNull(responseEntity);
    }

    @Test
    public void givenNothing_whenPostInvalid_then400() throws Exception {
        invoice.setAmount(0);
        invoice.setDate(null);
        invoice.setItems(null);

        DetailedInvoiceDto dto = invoiceMapper.invoiceToDetailedInvoiceDto(invoice);
        String body = objectMapper.writeValueAsString(dto);

        MvcResult mvcResult = this.mockMvc.perform(post(INVOICE_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(2, errors.length);
            }
        );
    }


}
