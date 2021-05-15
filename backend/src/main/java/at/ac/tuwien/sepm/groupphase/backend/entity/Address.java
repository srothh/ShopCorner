package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "street", nullable = false)
    @NotBlank
    private String street;
    @Column(name = "postal_code", nullable = false)
    @Digits(integer = 4, fraction = 0)
    private int postalCode;
    @Column(name = "house_number", nullable = false, length=64)
    @NotBlank
    private String houseNumber;
    @Column(name = "stair_number", columnDefinition = "BIGINT DEFAULT NULL")
    private int stairNumber;
    @Column(name = "door_number", columnDefinition = "VARCHAR(64) DEFAULT NULL")
    private String doorNumber;

    public Address() {
    }

    public Address(Long id, String street, int postalCode, String houseNumber, int stairNumber, String doorNumber) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.stairNumber = stairNumber;
        this.doorNumber = doorNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getStairNumber() {
        return stairNumber;
    }

    public void setStairNumber(int stairNumber) {
        this.stairNumber = stairNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }
}
