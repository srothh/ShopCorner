package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDto {
    private Long id;
    @NotNull(message = "Straße darf nicht null sein")
    @NotBlank(message = "Straße darf nicht leer sein")
    @Size(max = 255, message = "Straße darf nicht länger als 255 Zeichen sein")
    private String street;
    @NotNull(message = "PLZ darf nicht null sein")
    @Min(value = 1000, message = "Ungültige PLZ")
    @Max(value = 9999, message = "Ungültige PLZ")
    private int postalCode;
    @Size(max = 64, message = "Hausnummber darf nicht länger als 64 Zeichen sein")
    private String houseNumber;
    private int stairNumber;
    @Size(max = 64, message = "Türnummer darf nicht länger als 64 Zeichen sein")
    private String doorNumber;

    public AddressDto() {
    }

    public AddressDto(Long id, String street, int postalCode, String houseNumber, int stairNumber, String doorNumber) {
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
