package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Email;

public class ShopSettingsDto {
    @NotBlank
    @NotNull
    @Size(max = 50)
    String title;

    @NotBlank
    @NotNull
    String logo;

    @NotBlank
    @NotNull
    @Size(max = 128)
    String bannerTitle;

    @NotBlank
    @NotNull
    @Size(max = 255)
    String bannerText;

    @NotBlank
    @NotNull
    @Size(max = 128)
    String street;

    @NotBlank
    @NotNull
    @Size(max = 64)
    String houseNumber;

    int stairNumber;

    @Size(max = 64)
    String doorNumber;

    @NotNull
    @Min(value = 1000, message = "Invalid postal code")
    @Max(value = 9999, message = "Invalid postal code")
    int postalCode;

    @NotBlank
    @NotNull
    @Size(max = 100)
    String city;

    @NotBlank
    @NotNull
    @Size(max = 128)
    String phoneNumber;

    @NotBlank
    @NotNull
    @Email
    @Size(max = 255)
    String email;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
