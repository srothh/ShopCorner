package at.ac.tuwien.sepm.groupphase.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Category {
    @Id
    //@SequenceGenerator(name="category_sequence", sequenceName = "category_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    //@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    //@JsonIgnore
    //private Set<Product> products = new HashSet<>();

    public Category(){}

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

    /*public Set<Product> getProducts() {
        return products;
    }*/

    /*public void setProducts(Set<Product> products) {
        this.products = products;
    }*/

    //@PreRemove
    /* public void preRemove() {
        for (Product product : products) {
            product.setCategory(null);
        }
    }*/

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(id, category.id)
            && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category{"
            +
            "id=" + id
            +
            ", name='" + name + '\''
            +
            //", products=" + products +
            '}';
    }

    public static final class CategoryBuilder {
        private Long id;
        private String name;
        private Set<Product> products;

        public CategoryBuilder() {
        }

        public static CategoryBuilder getCategoryBuilder() {
            return new CategoryBuilder();
        }

        public CategoryBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            return category;
        }
    }
}
