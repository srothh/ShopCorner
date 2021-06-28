package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Email;

public class ShopSettingsDto {
    @NotBlank(message = "Es muss ein Titel angegeben werden.")
    @NotNull(message = "Es muss ein Titel angegeben werden.")
    @Size(max = 50, message = "Titel darf maximal 50 Zeichen lang sein.")
    private String title;

    @NotBlank(message = "Es muss ein Logo angegeben werden.")
    @NotNull(message = "Es muss ein Logo angegeben werden.")
    private String logo;

    @NotBlank(message = "Es muss ein Banner Titel angegeben werden.")
    @NotNull(message = "Es muss ein Banner Titel angegeben werden.")
    @Size(max = 128, message = "Banner Titel darf maximal 128 Zeichen lang sein.")
    private String bannerTitle;

    @NotBlank(message = "Es muss ein Banner Text angegeben werden.")
    @NotNull(message = "Es muss ein Banner Text angegeben werden.")
    @Size(max = 255, message = "Banner Text darf maximal 255 Zeichen lang sein.")
    private String bannerText;

    @NotBlank(message = "Es muss eine Strasse angegeben werden.")
    @NotNull(message = "Es muss eine Strasse angegeben werden.")
    @Size(max = 128, message = "Strasse darf maximal 128 Zeichen lang sein.")
    private String street;

    @NotBlank(message = "Es muss eine Hausnummer angegeben werden.")
    @NotNull(message = "Es muss eine Hausnummer angegeben werden.")
    @Size(max = 64, message = "Hausnummer darf maximal 64 Zeichen lang sein.")
    private String houseNumber;

    private int stairNumber;

    @Size(max = 64, message = "Türe darf maximal 64 Zeichen lang sein.")
    private String doorNumber;

    @NotNull(message = "Es muss eine Postleitzahl angegeben werden.")
    @Min(value = 1000, message = "Ungültige Postleitzahl")
    @Max(value = 9999, message = "Ungültige Postleitzahl")
    private int postalCode;

    @Size(max = 100, message = "Stadt darf maximal 100 Zeichen lang sein.")
    private String city;

    @NotBlank(message = "Es muss eine Telefonnummer angegeben werden.")
    @NotNull(message = "Es muss eine Telefonnummer angegeben werden.")
    @Size(max = 128, message = "Telefonnummer darf maximal 128 Zeichen lang sein.")
    private String phoneNumber;

    @NotBlank(message = "Es muss eine E-Mail angegeben werden.")
    @NotNull(message = "Es muss eine E-Mail angegeben werden.")
    @Email(message = "Die E-Mail ist ungültig.")
    @Size(max = 255, message = "Email darf maximal 255 Zeichen lang sein.")
    private String email;

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
