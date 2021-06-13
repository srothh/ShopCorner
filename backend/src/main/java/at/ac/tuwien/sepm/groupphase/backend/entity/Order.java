package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id", nullable = false, referencedColumnName = "id", updatable = false)
    private Invoice invoice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id", updatable = false)
    private Customer customer;

    public Order(Long id, Invoice invoiceId, Customer customerId) {
        this.id = id;
        this.invoice = invoiceId;
        this.customer = customerId;
    }

    public Order() {
    }

    public Order(Invoice invoiceId, Customer customerId) {
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

    public Customer getCustomerId() {
        return customer;
    }

    public void setCustomerId(Customer customerId) {
        this.customer = customerId;
    }
}
