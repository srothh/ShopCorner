package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import java.util.Properties;

public class ShopSettings {
    private static final String titleKey = "title";
    private static final String logoKey = "logo";
    private static final String bannerTitleKey = "bannerTitle";
    private static final String bannerTextKey = "bannerText";
    private static final String streetKey = "street";
    private static final String houseNumberKey = "houseNumber";
    private static final String stairNumberKey = "stairNumber";
    private static final String doorNumberKey = "doorNumber";
    private static final String postalCodeKey = "postalCode";
    private static final String cityKey = "city";
    private static final String phoneNumberKey = "phoneNumber";
    private static final String emailKey = "email";

    String title;
    String logo;
    String bannerTitle;
    String bannerText;
    String street;
    String houseNumber;
    int stairNumber;
    String doorNumber;
    int postalCode;
    String city;
    String phoneNumber;
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

    public void setProperties(Properties properties) {
        properties.setProperty(titleKey, this.title);
        properties.setProperty(logoKey, this.logo);
        properties.setProperty(bannerTitleKey, this.bannerTitle);
        properties.setProperty(bannerTextKey, this.bannerText);
        properties.setProperty(streetKey, this.street);
        properties.setProperty(houseNumberKey, this.houseNumber);
        properties.setProperty(stairNumberKey, Integer.toString(this.stairNumber));
        properties.setProperty(doorNumberKey, this.doorNumber);
        properties.setProperty(postalCodeKey, Integer.toString(this.postalCode));
        properties.setProperty(cityKey, this.city);
        properties.setProperty(phoneNumberKey, this.phoneNumber);
        properties.setProperty(emailKey, this.email);
    }

    public static ShopSettings buildFromProperties(Properties properties) {
        ShopSettingsBuilder builder = ShopSettingsBuilder.getShopSettingsBuilder();
        String title = properties.getProperty(titleKey, "DEFAULT_TITLE");
        String logo = properties.getProperty(logoKey, "DEFAULT_LOGO");
        String bannerTitle = properties.getProperty(bannerTitleKey, "DEFAULT_BANNER_TITLE");
        String bannerText = properties.getProperty(bannerTextKey, "DEFAULT_BANNER_TEXT");
        String street = properties.getProperty(streetKey, "DEFAULT_STREET");
        String houseNumber = properties.getProperty(houseNumberKey, "DEFAULT_HOUSENUMBER");
        int stairNumber = Integer.parseInt(properties.getProperty(stairNumberKey, "-1"));
        String doorNumber = properties.getProperty(doorNumberKey, "DEFAULT_DOORNUMBER");
        int postalCode = Integer.parseInt(properties.getProperty(postalCodeKey, "0000"));
        String city = properties.getProperty(cityKey, "DEFAULT_CITY");
        String phoneNumber = properties.getProperty(phoneNumberKey, "DEFAULT_PHONENUMBER");
        String email = properties.getProperty(emailKey, "DEFAULT_EMAIL");

        builder
            .withTitle(title)
            .withLogo(logo)
            .withBannerTitle(bannerTitle)
            .withBannerText(bannerText)
            .withStreet(street)
            .withHouseNumber(houseNumber)
            .withStairNumber(stairNumber)
            .withDoorNumber(doorNumber)
            .withPostalCode(postalCode)
            .withCity(city)
            .withPhoneNumber(phoneNumber)
            .withEmail(email);
        return builder.build();
    }

    public static final class ShopSettingsBuilder {
        String title;
        String logo;
        String bannerTitle;
        String bannerText;
        String street;
        String houseNumber;
        int stairNumber;
        String doorNumber;
        int postalCode;
        String city;
        String phoneNumber;
        String email;

        public ShopSettingsBuilder() {
        }

        public static ShopSettingsBuilder getShopSettingsBuilder() {
            return new ShopSettingsBuilder();
        }

        public ShopSettingsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ShopSettingsBuilder withLogo(String logo) {
            this.logo = logo;
            return this;
        }

        public ShopSettingsBuilder withBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
            return this;
        }

        public ShopSettingsBuilder withBannerText(String bannerText) {
            this.bannerText = bannerText;
            return this;
        }

        public ShopSettingsBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public ShopSettingsBuilder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public ShopSettingsBuilder withStairNumber(int stairNumber) {
            this.stairNumber = stairNumber;
            return this;
        }

        public ShopSettingsBuilder withDoorNumber(String doorNumber) {
            this.doorNumber = doorNumber;
            return this;
        }

        public ShopSettingsBuilder withPostalCode(int postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ShopSettingsBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ShopSettingsBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ShopSettingsBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ShopSettings build() {
            ShopSettings shopSettings = new ShopSettings();
            shopSettings.setTitle(title);
            shopSettings.setLogo(logo);
            shopSettings.setBannerTitle(bannerTitle);
            shopSettings.setBannerText(bannerText);
            shopSettings.setStreet(street);
            shopSettings.setHouseNumber(houseNumber);
            shopSettings.setStairNumber(stairNumber);
            shopSettings.setDoorNumber(doorNumber);
            shopSettings.setPostalCode(postalCode);
            shopSettings.setCity(city);
            shopSettings.setPhoneNumber(phoneNumber);
            shopSettings.setEmail(email);
            return shopSettings;
        }
    }
}