package at.ac.tuwien.sepm.groupphase.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;


@Entity
public class InvoiceItem {
    @EmbeddedId
    private InvoiceItemKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @JsonIgnore
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int numberOfItems;

    public InvoiceItem() {
        invoice = new Invoice();
        product = new Product();
    }

    public InvoiceItemKey getId() {
        return id;
    }

    public void setId(InvoiceItemKey id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @Override
    public String toString() {
        return invoice.toString() + " " + product.toString() + " " + numberOfItems;
    }
}
