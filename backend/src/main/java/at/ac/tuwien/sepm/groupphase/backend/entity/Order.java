package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id", nullable = false, referencedColumnName = "id", updatable = false)
    private Invoice invoice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId", nullable = false, referencedColumnName = "id", updatable = false)
    private Long customer;

    public Order(Long id, Invoice invoiceId, Long customerId) {
        this.id = id;
        this.invoice = invoiceId;
        this.customer = customerId;
    }

    public Order() {
    }

    public Order(Invoice invoiceId, Long customerId) {
        this.invoice = invoiceId;
        this.customer = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoiceId() {
        return invoice;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoice = invoiceId;
    }

    public Long getCustomerId() {
        return customer;
    }

    public void setCustomerId(Long customerId) {
        this.customer = customerId;
    }
}
