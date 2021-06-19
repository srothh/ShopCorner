package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DetailedInvoiceDto extends SimpleInvoiceDto {

    @NotNull(message = "Set<InvoiceItemDto> can not be null")
    private Set<InvoiceItemDto> items = new HashSet<>();


    public Set<InvoiceItemDto> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItemDto> invoiceItem) {
        this.items = invoiceItem;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DetailedInvoiceDto that = (DetailedInvoiceDto) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), items);
    }

}
