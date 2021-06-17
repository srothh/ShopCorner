package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class SimpleInvoiceDto {

    private Long id;

    private String invoiceNumber;

    private String orderNumber;

    private Long customerId;

    @NotNull(message = "LocalDateTime can not be null")
    private LocalDateTime date;

    @DecimalMin(value = "0.01", message = "The amount can not be smaller than 0.01")
    private double amount;

    @NotNull(message = "InvoiceType must not be null")
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleInvoiceDto that = (SimpleInvoiceDto) o;
        return Double.compare(that.amount, amount) == 0 && id.equals(that.id) && date.equals(that.date) && invoiceType.equals(that.invoiceType)
            && orderNumber.equals(that.orderNumber) && customerId.equals(that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, invoiceType, orderNumber, invoiceNumber, customerId);
    }

    @Override
    public String toString() {
        return "SimpleInvoiceDto{" + "id=" + id + ", invoiceNumber=" + invoiceNumber + ", date=" + date + ", amount=" + amount + ", type=" + invoiceType +  '}';
    }
}
