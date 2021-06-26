package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class InvoiceArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String invoiceNumber;

    @Lob
    @Column(name = "pdfByte", length = 3000)
    private byte[] invoiceAsPdf;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public byte[] getInvoiceAsPdf() {
        return invoiceAsPdf;
    }

    public void setInvoiceAsPdf(byte[] invoiceAsPdf) {
        this.invoiceAsPdf = invoiceAsPdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceArchive that = (InvoiceArchive) o;
        return Objects.equals(id, that.id) && Objects.equals(invoiceNumber, that.invoiceNumber) && Arrays.equals(invoiceAsPdf, that.invoiceAsPdf);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, invoiceNumber);
        result = 31 * result + Arrays.hashCode(invoiceAsPdf);
        return result;
    }
}
