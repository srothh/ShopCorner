package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;

import java.util.Objects;
import java.util.Set;

public class DetailedInvoiceDto extends SimpleInvoiceDto{

    private Set<InvoiceItem> items;

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DetailedInvoiceDto that = (DetailedInvoiceDto) o;
        return items.equals(that.items);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), items);
    }

    @Override
    public String toString() {
        return "DetailedInvoiceDto{" +
            "items=" + items +
            '}';
    }
}
