package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class OrderDto {
    private Long id;
    private CustomerDto customer;
    private DetailedInvoiceDto invoice;

    public OrderDto(Long id, CustomerDto customer, DetailedInvoiceDto invoice) {
        this.id = id;
        this.invoice = invoice;
        this.customer = customer;
    }

    public OrderDto() {
    }

    public OrderDto(CustomerDto customer, DetailedInvoiceDto invoice) {
        this.invoice = invoice;
        this.customer = customer;
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
