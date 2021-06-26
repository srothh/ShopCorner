package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
