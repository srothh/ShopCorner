package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class OrderDto {


    private Long id;
    @NotNull
    private DetailedInvoiceDto invoice;
    @NotNull
    private CustomerDto customer;

    public OrderDto(Long id, DetailedInvoiceDto invoiceId, CustomerDto customerId) {
        this.id = id;
        this.invoice = invoiceId;
        this.customer = customerId;
    }

    public OrderDto() {
    }

    public OrderDto(DetailedInvoiceDto invoiceId, CustomerDto customerId) {
        this.invoice = invoiceId;
        this.customer = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetailedInvoiceDto getInvoiceId() {
        return invoice;
    }

    public void setInvoiceId(DetailedInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public CustomerDto getCustomerId() {
        return customer;
    }

    public void setCustomerId(CustomerDto customerId) {
        this.customer = customerId;
    }
}