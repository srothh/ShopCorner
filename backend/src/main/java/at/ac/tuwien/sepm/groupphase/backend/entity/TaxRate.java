package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tax_rate")
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double percentage;
    @OneToMany(mappedBy = "taxRate", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();


    public TaxRate(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxRate)) {
            return false;
        }
        TaxRate taxRate = (TaxRate) o;
        return Objects.equals(id, taxRate.id)
            && Objects.equals(percentage, taxRate.percentage);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentage);
    }

    @Override
    public String toString() {
        return "TaxRate{"
            +
            "id=" + id
            +
            ", percentage=" + percentage
            +
            '}';
    }

    public static final class TaxRateBuilder {
        public Long id;
        public Double percentage;

        public TaxRateBuilder(){}

        public static TaxRateBuilder getTaxRateBuilder() {
            return new TaxRateBuilder();
        }

        public TaxRateBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaxRateBuilder withPercentage(Double percentage) {
            this.percentage = percentage;
            return this;
        }

        public TaxRate build() {
            TaxRate taxRate = new TaxRate();
            taxRate.setId(id);
            taxRate.setPercentage(percentage);
            return taxRate;
        }
    }
}
