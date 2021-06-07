package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TaxRateDto {
    private Long id;
    private Double percentage;
    private Double calculationFactor;
    //private Set<Product> products = new HashSet<>();

    public Double getCalculationFactor() {
        return calculationFactor;
    }

    public void setCalculationFactor(Double calculationFactor) {
        this.calculationFactor = calculationFactor;
    }

    public TaxRateDto(){}

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

    /*public Set<Product> getProducts() {
        return products;
    }*/

    /*public void setProducts(Set<Product> products) {
        this.products = products;
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxRateDto)) {
            return false;
        }
        TaxRateDto taxRateDto = (TaxRateDto) o;
        return Objects.equals(id, taxRateDto.id)
            && Objects.equals(percentage, taxRateDto.percentage);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentage);
    }

    @Override
    public String toString() {
        return "TaxRateDto{"
            +
            "id=" + id
            +
            ", percentage=" + percentage
            +
            '}';
    }
}
