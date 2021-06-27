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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id", referencedColumnName = "id", updatable = false)
    private Promotion promotion;

    public Order(Long id, Invoice invoice, Customer customer) {
        this.id = id;
        this.invoice = invoice;
        this.customer = customer;
    }

    public Order() {
    }

    public Order(Invoice invoice, Customer customer) {
        this.invoice = invoice;
        this.customer = customer;
    }

    public Order(Invoice invoice, Customer customer, Promotion promotion) {
        this.invoice = invoice;
        this.customer = customer;
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoiceId) {
        this.invoice = invoiceId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customerId) {
        this.customer = customerId;
    }
}
