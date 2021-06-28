package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceItemKey implements Serializable {

    @NotNull(message = "invoiceId darf nicht null sein")
    @Column(name = "invoice_id")
    private Long invoiceId;

    @NotNull(message = "productId darf nicht null sein")
    @Column(name = "product_id", nullable = false)
    private Long productId;

    public InvoiceItemKey() {
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceItemKey that = (InvoiceItemKey) o;
        return Objects.equals(invoiceId, that.invoiceId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, productId);
    }

    @Override
    public String toString() {
        return "InvoiceItemKey{" + "invoiceId=" + invoiceId + ", productId=" + productId + '}';
    }
}
