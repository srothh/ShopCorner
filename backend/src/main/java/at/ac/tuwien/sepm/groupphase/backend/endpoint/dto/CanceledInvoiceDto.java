package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoiceItem;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CanceledInvoiceDto {

    private Long id;

    @NotNull
    @NotBlank
    private String invoiceNumber;

    @NotNull
    @NotBlank
    private String orderNumber;

    private Long customerId;

    private LocalDateTime date;

    @DecimalMin(value = "0.01", message = "The amount can not be smaller than 0.01")
    private double subtotal;

    @DecimalMin(value = "0.01", message = "The amount can not be smaller than 0.01")
    private double taxAmount;

    @DecimalMin(value = "0.01", message = "The amount can not be smaller than 0.01")
    private double total;

    @NotNull
    Set<CanceledInvoiceItem> canceledInvoiceItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Set<CanceledInvoiceItem> getCanceledInvoiceItems() {
        return canceledInvoiceItems;
    }

    public void setCanceledInvoiceItems(Set<CanceledInvoiceItem> canceledInvoiceItems) {
        this.canceledInvoiceItems = canceledInvoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanceledInvoiceDto that = (CanceledInvoiceDto) o;
        return Double.compare(that.subtotal, subtotal) == 0 && Double.compare(that.taxAmount, taxAmount) == 0 && Double.compare(that.total, total) == 0 && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(invoiceNumber, that.invoiceNumber) && Objects.equals(orderNumber, that.orderNumber) && Objects.equals(customerId, that.customerId) && Objects.equals(canceledInvoiceItems, that.canceledInvoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceNumber, orderNumber, date, customerId, subtotal, taxAmount, total, canceledInvoiceItems);
    }
}
