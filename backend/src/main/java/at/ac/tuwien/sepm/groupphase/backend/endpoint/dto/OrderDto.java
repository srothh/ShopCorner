package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OrderDto {
    private Long id;
    @Valid
    @NotNull
    private CustomerDto customer;
    @Valid
    @NotNull
    private DetailedInvoiceDto invoice;
    private PromotionDto promotion;

    public OrderDto(Long id, CustomerDto customer, DetailedInvoiceDto invoice) {
        this.id = id;
        this.invoice = invoice;
        this.customer = customer;
    }

    public OrderDto(Long id, CustomerDto customer, DetailedInvoiceDto invoice, PromotionDto promotion) {
        this.id = id;
        this.invoice = invoice;
        this.customer = customer;
        this.promotion = promotion;
    }

    public OrderDto() {
    }

    public OrderDto(CustomerDto customer, DetailedInvoiceDto invoice) {
        this.invoice = invoice;
        this.customer = customer;
    }

    public PromotionDto getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDto promotion) {
        this.promotion = promotion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetailedInvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(DetailedInvoiceDto invoice) {
        this.invoice = invoice;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customerId) {
        this.customer = customerId;
    }
}
