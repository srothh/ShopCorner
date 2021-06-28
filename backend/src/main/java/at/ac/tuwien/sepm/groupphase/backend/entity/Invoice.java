package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(unique = true)
    private String orderNumber;

    private Long customerId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private double amount;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<InvoiceItem> items;

    @NotNull(message = "InvoiceType darf nicht null sein")
    @Column(name = "invoiceType")
    @Enumerated(EnumType.STRING)
    InvoiceType invoiceType;

    public Invoice() {
        items = new HashSet<>();
    }

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

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
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
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.amount, amount) == 0 && id.equals(invoice.id) && invoiceNumber.equals(invoice.invoiceNumber)
            && date.equals(invoice.date) && items.equals(invoice.items) && invoiceType.equals(invoice.invoiceType) && orderNumber.equals(invoice.orderNumber) && customerId.equals(invoice.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceNumber, date, amount, items, invoiceType, orderNumber, customerId);
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", invoiceNumber= " + invoiceNumber + ", date=" + date + ", amount=" + amount + ", typ=" + invoiceType + ", customer=" + customerId + '}';
    }
}
