package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "discount", nullable = false)
    private double discount;
    @Column(name = "creationDate", nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Column(name = "expirationDate", nullable = false)
    private LocalDateTime expirationDate;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "minimumOrderValue")
    private double minimumOrderValue;

    public Promotion() {
    }

    public Promotion(String name, double discount, LocalDateTime creationDate, LocalDateTime expirationDate, String code, double minimumOrderValue) {
        this.name = name;
        this.discount = discount;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.code = code;
        this.minimumOrderValue = minimumOrderValue;
    }

    public Promotion(Long id, String name, double discount, LocalDateTime creationDate, LocalDateTime expirationDate, String code, double minimumOrderValue) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.code = code;
        this.minimumOrderValue = minimumOrderValue;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(double minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }
}
