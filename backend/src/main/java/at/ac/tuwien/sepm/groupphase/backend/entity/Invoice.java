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

    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceItem> items;

    public Invoice(Long id, LocalDateTime date, double amount, Set<InvoiceItem> items) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.items = items;
    }

    public Invoice() {

    }
}
