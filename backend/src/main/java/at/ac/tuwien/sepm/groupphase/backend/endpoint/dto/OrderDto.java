package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class OrderDto {


    private Long id;
    @NotNull
    private DetailedInvoiceDto invoice;
    @NotNull
    private Long customerId;

    public OrderDto(Long id, DetailedInvoiceDto invoiceId, Long customerId) {
        this.id = id;
        this.invoice = invoiceId;
        this.customerId = customerId;
    }

    public OrderDto() {
    }

    public OrderDto(DetailedInvoiceDto invoiceId, Long customerId) {
        this.invoice = invoiceId;
        this.customerId = customerId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
