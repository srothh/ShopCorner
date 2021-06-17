package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.PreRemove;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50, message = "name should contain at least 3 characters and 50 at most")
    private String name;

    @Size(max = 200)
    private String description;

    @DecimalMin("0.0")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tax_rate", nullable = false)
    private TaxRate taxRate;

    @Lob
    private byte[] picture;

    @Column(name = "saleCount", columnDefinition = "BIGINT default 0")
    private Long saleCount;

    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;

    private boolean deleted;
    private boolean locked;


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

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
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

    public static final class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Category category;
        private boolean locked;
        private TaxRate taxRate;
        private byte[] picture;
        private LocalDateTime expiresAt;
        private boolean deleted;

        public ProductBuilder(){
        }

        public static ProductBuilder getProductBuilder() {
            return new ProductBuilder();
        }



        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder withTaxRate(TaxRate taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public ProductBuilder withExpiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public ProductBuilder withLocked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public ProductBuilder withDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public ProductBuilder withPicture(byte[] picture) {
            this.picture = picture;
            return this;
        }

        public ProductBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setTaxRate(taxRate);
            product.setLocked(locked);
            product.setPicture(picture);
            product.setDeleted(deleted);
            product.setExpiresAt(expiresAt);
            return product;
        }
    }
}
