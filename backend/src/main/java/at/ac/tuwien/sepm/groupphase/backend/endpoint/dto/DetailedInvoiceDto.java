package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DetailedInvoiceDto extends SimpleInvoiceDto {
    private Set<Product> items;

    public Set<Product> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> invoiceItems) {
        if (this.items == null) {
            items = new HashSet<>();
        }
        for (InvoiceItem i : invoiceItems) {
            System.out.println(i.getProduct().toString());
            this.items.add(i.getProduct());
        }
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

    @Override
    public String toString() {
        String s = "";
        if (items == null) {
            return null;
        }
        for (Product i : items) {
            s = s + i.toString() + "\n";
        }
        return s;
    }

    /*public static final class DetailedInvoiceDtoBuilder {
        private Long id;
        private LocalDateTime date;
        private double amount;
        private Set<InvoiceItem> items;

        public static DetailedInvoiceDto.DetailedInvoiceDtoBuilder aDetailedInvoiceDto() {
            return new DetailedInvoiceDto.DetailedInvoiceDtoBuilder();
        }

        public DetailedInvoiceDto.DetailedInvoiceDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public DetailedInvoiceDto.DetailedInvoiceDtoBuilder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public DetailedInvoiceDto.DetailedInvoiceDtoBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public DetailedInvoiceDto.DetailedInvoiceDtoBuilder withInvoiceItem(Set<InvoiceItem> items) {
            this.items = items;
            return this;
        }

        public DetailedInvoiceDto build() {
            DetailedInvoiceDto detailedInvoiceDto = new DetailedInvoiceDto();
            detailedInvoiceDto.setId(id);
            detailedInvoiceDto.setDate(date);
            detailedInvoiceDto.setItems(items);
            detailedInvoiceDto.setAmount(amount);
            return detailedInvoiceDto;
        }
    }*/
}
