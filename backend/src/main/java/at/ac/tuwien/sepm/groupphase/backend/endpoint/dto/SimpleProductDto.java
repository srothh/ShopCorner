package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

// DTO Class for Product without picture and Category
public class SimpleProductDto {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20, message = "name should contain at least 3 characters and 20 at most")
    private String name;
    @Size(max = 70)
    private String description;
    @DecimalMin("0.0")
    private Double price;
    private TaxRate taxRate;
    private boolean deleted;


    public SimpleProductDto() {
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleProductDto)) {
            return false;
        }
        SimpleProductDto simpleProductDto = (SimpleProductDto) o;
        return Objects.equals(id, simpleProductDto.id)
            && Objects.equals(name, simpleProductDto.name)
            && Objects.equals(description, simpleProductDto.description)
            && Objects.equals(price, simpleProductDto.price)
            && Objects.equals(taxRate, simpleProductDto.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, taxRate);
    }

    @Override
    public String toString() {
        return "SimpleProductDto{"
            +
            "id=" + id
            +
            ", name='" + name + '\''
            +
            ", description='" + description + '\''
            +
            ", amount=" + price
            +
            ", taxRate=" + taxRate
            +
            '}';
    }
}
