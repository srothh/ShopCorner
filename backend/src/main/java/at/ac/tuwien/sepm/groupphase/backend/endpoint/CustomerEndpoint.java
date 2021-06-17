package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CustomerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.service.CustomerService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Autowired
    public CustomerEndpoint(CustomerMapper customerMapper, CustomerService customerService, InvoiceService invoiceService, OrderService orderService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.orderService = orderService;
    }

    /**
     * Adds a new customer to the database.
     *
     * @param dto The customer dto containing the customer information
     * @return The response dto containing the added customer
     */
    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new customer account")
    public CustomerRegistrationDto registerNewCustomer(@Valid @RequestBody CustomerRegistrationDto dto) {
        LOGGER.info("POST api/v1/customers");
        CustomerRegistrationDto customer = customerMapper.customerToCustomerRegistrationDto(customerService.registerNewCustomer(customerMapper.customerDtoToCustomer(dto)));
        customer.setPassword(null);
        return customer;
    }

    /**
     * Retrieves a page of customers from the database.
     *
     * @return A page of customers
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve pages of customers", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<CustomerDto> getAllCustomersPerPage(@Valid PaginationRequestDto paginationRequestDto) {
        int page = paginationRequestDto.getPage();
        int pageCount = paginationRequestDto.getPageCount();
        LOGGER.info("GET api/v1/customers?page={}&page_count={}", page, pageCount);
        Page<Customer> customerPage = customerService.getAllCustomers(page, pageCount);
        return new PaginationDto<>(customerMapper.customerListToCustomerDtoList(customerPage.getContent()), page, pageCount, customerPage.getTotalPages(), customerService.getCustomerCount());
    }

    /**
     * Retrieves the amount of customers in the database.
     *
     * @return The amount of customers in the database
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve count of customers", security = @SecurityRequirement(name = "apiKey"))
    public long getCustomerCount() {
        LOGGER.info("GET api/v1/customers");
        return customerService.getCustomerCount();
    }

    /**
     * Get specific Customer of a specific order from invoiceId.
     *
     * @param invoiceId is the id of the invoice
     * @return DetailedInvoiceDto with all given information of the invoice
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve customer from order", security = @SecurityRequirement(name = "apiKey"))
    public CustomerDto getCustomerFromOrderByInvoiceId(@RequestParam(name = "invoice") Long invoiceId) {
        LOGGER.info("GET api/v1/invoices?invoice={}", invoiceId);
        Invoice invoice = this.invoiceService.findOneById(invoiceId);
        Customer customer = this.orderService.getOrderByInvoice(invoice).getCustomer();
        return this.customerMapper.customerToCustomerDto(customer);
    }

}
