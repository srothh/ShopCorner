package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UpdatePasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.security.Principal;

/**
 * The MeEndpoint is responsible for handling requests to resources associated with a customer.
 * All requests need to include the access token in the header.
 */
@RestController
@RequestMapping(MeEndpoint.BASE_URL)
public class MeEndpoint {

    static final String BASE_URL = "api/v1/me";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final InvoiceService invoiceService;
    private final PdfGeneratorService pdfGeneratorService;

    @Autowired
    public MeEndpoint(CustomerMapper customerMapper,
                      CustomerService customerService,
                      OrderService orderService,
                      OrderMapper orderMapper,
                      InvoiceService invoiceService,
                      @Lazy PdfGeneratorService pdfGeneratorService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.invoiceService = invoiceService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    /**
     * Finds a customer by login name.
     *
     * @param principal the currently logged in user
     * @return the customer with the given login name
     */
    @Secured({"ROLE_CUSTOMER"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve customer data", security = @SecurityRequirement(name = "apiKey"))
    public CustomerDto findCustomerByLoginName(Principal principal) {
        LOGGER.info("GET " + BASE_URL);
        Customer entity = customerService.findCustomerByLoginName(principal.getName());
        return customerMapper.customerToCustomerDto(entity);
    }

    /**
     * Finds an invoice of the customer and generates a PDF from it.
     *
     * @param paginationRequestDto the dto containing pagination parameters
     * @param principal the currently logged in user
     * @return paginated Orders with the given pagination parameters
     */
    @Secured({"ROLE_CUSTOMER"})
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all orders from the customer", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<OrderDto> findAllOrders(Principal principal, PaginationRequestDto paginationRequestDto) {
        LOGGER.info("GET " + BASE_URL + "/orders");
        String username = principal.getName();
        int page = paginationRequestDto.getPage();
        int pageSize = paginationRequestDto.getPageCount();
        Customer customer = customerService.findCustomerByLoginName(username);
        Page<Order> orderPage = this.orderService.getAllOrdersByCustomer(page, pageSize, customer.getId());
        return new PaginationDto<>(this.orderMapper.orderListToOrderDtoList(orderPage.getContent()), page, pageSize, orderPage.getTotalPages(), orderPage.getTotalElements());
    }

    /**
     * Finds an invoice of the customer and generates a PDF from it.
     *
     * @param id id of the invoice
     * @param principal the currently logged in user
     * @return ResponseEntity with the generated pdf for the customer
     */
    @Secured({"ROLE_CUSTOMER"})
    @GetMapping(value = "/invoices/{id}/pdf", produces = "application/pdf")
    @Operation(summary = "Retrieve new invoice as pdf", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<byte[]> getInvoiceAsPdf(@PathVariable Long id, Principal principal) {
        LOGGER.info("GET /api/v1/me/invoices/{}/pdf", id);
        String username = principal.getName();
        Customer customer = customerService.findCustomerByLoginName(username);
        Invoice invoice = invoiceService.getByIdAndCustomerId(id, customer.getId());
        return new ResponseEntity<>(pdfGeneratorService.createPdfInvoiceCustomerFromInvoice(invoice), this.generateHeader(), HttpStatus.OK);
    }

    /**
     * Deletes a customer from the database.
     *
     * @param principal the currently logged in user
     */
    @Secured({"ROLE_CUSTOMER"})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete customer", security = @SecurityRequirement(name = "apiKey"))
    public void deleteCustomerByUsername(Principal principal) {
        LOGGER.info("DELETE " + BASE_URL);
        customerService.deleteCustomerByLoginName(principal.getName());
    }

    /**
     * Edits an already existing Customer.
     *
     * @param customerDto the dto containing the updated fields
     * @param principal the currently logged in user
     * @return the updated customer entry
     */
    @Secured({"ROLE_CUSTOMER"})
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit an existing customer account", security = @SecurityRequirement(name = "apiKey"))
    public CustomerDto editCustomer(@Valid @RequestBody CustomerDto customerDto, Principal principal) {
        LOGGER.info("POST " + BASE_URL + " body: {}", customerDto);
        Customer customer = customerMapper.dtoToCustomer(customerDto);
        if (customerService.findCustomerByLoginName(principal.getName()).getId().equals(customer.getId())) {
            return customerMapper.customerToCustomerDto(customerService.update(customer));
        }

        throw new AccessDeniedException("Unberechtigter Zugriff");

    }

    /**
     * Update the password of an existing operator.
     *
     * @param updatePasswordDto the old and new password
     * @param principal the currently logged in user
     */
    @Secured({"ROLE_CUSTOMER"})
    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the password of an existing customer account", security = @SecurityRequirement(name = "apiKey"))
    public void updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto, Principal principal) {
        LOGGER.info("POST " + BASE_URL + "/password body: {}", updatePasswordDto);
        Customer customer = customerService.findCustomerByLoginName(principal.getName());
        customerService.updatePassword(customer.getId(), updatePasswordDto.getOldPassword(),
            updatePasswordDto.getNewPassword());
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
