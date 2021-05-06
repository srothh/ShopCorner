package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "date")
    private LocalDateTime date;

    @Column(nullable = false)
    private double amount;

    @ManyToMany
    @JoinTable(
        name = "invoice_item",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Products> items;

    public Invoice(Long id, LocalDateTime date, double amount, Set<Products> items) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.items = items;
    }

    public Invoice() {

    }
}
