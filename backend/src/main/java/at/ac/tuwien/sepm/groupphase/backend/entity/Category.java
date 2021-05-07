package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @SequenceGenerator(name="category_sequence", sequenceName = "category_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "category" )
    private Set<Product> products = new HashSet<>();
}
