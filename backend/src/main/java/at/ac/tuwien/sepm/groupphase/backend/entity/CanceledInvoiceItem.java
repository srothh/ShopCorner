package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class CanceledInvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "1", message = "The number of items can not be zero")
    private int numberOfItems;

    @NotNull
    private String productName;

    @DecimalMin(value = "1.0", message = "The tax can not be smaller than 1.0")
    private double productTax;

    @DecimalMin(value = "1.0", message = "The price can not be smaller than 1.0")
    private double productPrice;

    public CanceledInvoiceItem(int numberOfItems, String productName, double productTax, double productPrice) {
        this.numberOfItems = numberOfItems;
        this.productName = productName;
        this.productTax = productTax;
        this.productPrice = productPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductTax() {
        return productTax;
    }

    public void setProductTax(double productTax) {
        this.productTax = productTax;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanceledInvoiceItem that = (CanceledInvoiceItem) o;
        return Double.compare(that.productTax, productTax) == 0 && Double.compare(that.productPrice, productPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(numberOfItems, that.numberOfItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfItems, productName, productTax, productPrice);
    }
}
