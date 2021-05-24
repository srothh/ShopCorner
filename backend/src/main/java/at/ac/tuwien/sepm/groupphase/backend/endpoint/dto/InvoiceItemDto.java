package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItemKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public class InvoiceItemDto {

    @NotNull(message = "InvoiceItemKey is mandatory")
    private InvoiceItemKey id;

    @NotNull(message = "Invoice is mandatory")
    private Invoice invoice;

    @NotNull(message = "Product is mandatory")
    private Product product;

    @NotNull(message = "Number of Items is mandatory")
    @NotBlank
    @Min(value = 1, message = "Minimum number of items is 1")
    private int numberOfItems;

    public InvoiceItemDto() {
        this.invoice = new Invoice();
        this.product = new Product();
    }

    public InvoiceItemDto(Invoice invoice, Product product) {
        this.invoice = invoice;
        this.product = product;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceItemDto that = (InvoiceItemDto) o;
        return numberOfItems == that.numberOfItems && Objects.equals(id, that.id) && Objects.equals(invoice, that.invoice) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoice, product, numberOfItems);
    }
}
