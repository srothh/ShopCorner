package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20, message = "name should contain at least 3 characters and 20 at most")
    private String name;
    @Size(max = 70)
    private String description;
    @DecimalMin("0.0")
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;
    private boolean locked;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tax_rate", nullable = false)
    private TaxRate taxRate;
    @Lob
    private byte[] picture;

    public Product() {
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double amount) {
        this.price = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id)
            && Objects.equals(name, product.name);

    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Product{"
            +
            "id=" + id
            +
            ", name='" + name + '\''
            +
            ", description='" + description + '\''
            +
            ", amount=" + price
            +
            ", category=" + category
            +
            ", isLocked=" + locked
            +
            ", taxRate=" + taxRate
            +
            '}';
    }
}
