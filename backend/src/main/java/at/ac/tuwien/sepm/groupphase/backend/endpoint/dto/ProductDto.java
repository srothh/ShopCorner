package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ProductDto {
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Double price;
    private Category category;
    private TaxRate taxRate;

    public ProductDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDto)) {
            return false;
        }
        ProductDto productDto = (ProductDto) o;
        return Objects.equals(id, productDto.id)
            && Objects.equals(name, productDto.name)
            && Objects.equals(description, productDto.description)
            && Objects.equals(price, productDto.price)
            && Objects.equals(category, productDto.category)
            && Objects.equals(taxRate, productDto.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, category, taxRate);
    }

    @Override
    public String toString() {
        return "ProductDto{"
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
            ", taxRate=" + taxRate
            +
            '}';
    }
}



