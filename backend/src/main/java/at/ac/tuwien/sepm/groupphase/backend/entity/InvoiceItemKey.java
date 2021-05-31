package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceItemKey implements Serializable {

    @NotNull(message = "InvoiceItemKey invoiceId can not be null")
    @Column(name = "invoice_id")
    Long invoiceId;

    @NotNull(message = "InvoiceItemKey productId can not be null")
    @Column(name = "product_id", nullable = false)
    Long productId;

    public InvoiceItemKey() {
    }

    public InvoiceItemKey(Long invoiceId, Long productId) {
        this.invoiceId = invoiceId;
        this.productId = productId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getProductId() {
        return productId;
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