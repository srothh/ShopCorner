package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import java.util.Properties;

public class ShopSettings {
    private static final String titleKey = "title";
    private static final String logoKey = "logo";
    private static final String bannerTitleKey = "bannerTitle";
    private static final String bannerTextKey = "bannerText";

    String title;
    String logo;
    String bannerTitle;
    String bannerText;

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

    public void setProperties(Properties properties) {
        properties.setProperty(titleKey, this.title);
        properties.setProperty(logoKey, this.logo);
        properties.setProperty(bannerTitleKey, this.bannerTitle);
        properties.setProperty(bannerTextKey, this.bannerText);
    }

    public static ShopSettings buildFromProperties(Properties properties) {
        ShopSettingsBuilder builder = ShopSettingsBuilder.getShopSettingsBuilder();
        String title = properties.getProperty(titleKey, "DEFAULT_TITLE");
        String logo = properties.getProperty(logoKey, "DEFAULT_LOGO");
        String bannerTitle = properties.getProperty(bannerTitleKey, "DEFAULT_BANNER_TITLE");
        String bannerText = properties.getProperty(bannerTextKey, "DEFAULT_BANNER_TEXT");
        builder
            .withTitle(title)
            .withLogo(logo)
            .withBannerTitle(bannerTitle)
            .withBannerText(bannerText);
        return builder.build();
    }

    public static final class ShopSettingsBuilder {
        String title;
        String logo;
        String bannerTitle;
        String bannerText;

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

        public ShopSettings build() {
            ShopSettings shopSettings = new ShopSettings();
            shopSettings.setTitle(title);
            shopSettings.setLogo(logo);
            shopSettings.setBannerTitle(bannerTitle);
            shopSettings.setBannerText(bannerText);
            return shopSettings;
        }
    }
}