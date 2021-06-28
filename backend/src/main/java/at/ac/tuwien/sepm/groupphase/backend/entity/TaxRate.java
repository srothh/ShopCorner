package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

@Entity
@Table(name = "tax_rate")
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double percentage;
    private Double calculationFactor;


    public Double getCalculationFactor() {
        return calculationFactor;
    }

    public void setCalculationFactor(Double calculationFactor) {
        this.calculationFactor = calculationFactor;
    }

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
        Long id;
        Double percentage;
        Double calculationFactor;

        TaxRateBuilder(){}

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

        public TaxRateBuilder withCalculationFactor(Double calculationFactor) {
            this.calculationFactor = calculationFactor;
            return this;
        }

        public TaxRate build() {
            TaxRate taxRate = new TaxRate();
            taxRate.setId(id);
            taxRate.setPercentage(percentage);
            taxRate.setCalculationFactor(calculationFactor);
            return taxRate;
        }
    }
}
