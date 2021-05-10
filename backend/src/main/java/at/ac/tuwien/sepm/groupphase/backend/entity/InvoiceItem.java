package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class InvoiceItem {
    @EmbeddedId
    private InvoiceItemKey id;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id",referencedColumnName = "id")
    private Invoice invoice;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    private int numberOfItems;

    public InvoiceItem(){
        invoice = new Invoice();
        product =new Product();

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


}
