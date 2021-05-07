package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryDto {

    private Long id;
    private String name;
    private Set<Product> products = new HashSet<>();

    public CategoryDto(){}

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDto)) {
            return false;
        }
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(id, categoryDto.id)
            && Objects.equals(name, categoryDto.name)
            && Objects.equals(products,categoryDto.products);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, products);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", products=" + products +
            '}';
    }
}
