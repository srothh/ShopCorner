package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryDto {

    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(min = 3, max = 20, message = "Name muss zwischen 3 und 20 Zeichen lang sein")
    private String name;

    public CategoryDto() {
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDto)) {
            return false;
        }
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(id, categoryDto.id)
            && Objects.equals(name, categoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryDto{"
            +
            "id=" + id
            +
            ", name='" + name + '\''
            +
            '}';
    }
}
