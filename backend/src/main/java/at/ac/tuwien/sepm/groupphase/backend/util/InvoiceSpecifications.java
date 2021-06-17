package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceType;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecifications {

    private InvoiceSpecifications(){}


    /**
     * returns specification that searches for given invoiceType.
     *
     * @param invoiceType that need to be searched for
     * @return specification (includes root, criteriaQuery and criteriaBuilder)
     */
    public static Specification<Invoice> hasInvoiceType(InvoiceType invoiceType) {
        return (invoice, cq, cb) -> cb.equal(invoice.get("invoiceType"), invoiceType);
    }
}
