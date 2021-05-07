package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InvoiceItemKey implements Serializable {

    @Column(name="invoice_id")
    Long invoiceId;
    @Column(name = "product_id")
    Long productId;
}
