package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_fk")
    private Category category;
    private Double taxRate;

    public Product() {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
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
            && Objects.equals(name, product.name)
            && Objects.equals(description, product.description)
            && Objects.equals(amount, product.amount)
            && Objects.equals(category, product.category)
            && Objects.equals(taxRate, product.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, amount, category, taxRate);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", amount=" + amount +
            ", category=" + category +
            ", taxRate=" + taxRate +
            '}';
    }
}
