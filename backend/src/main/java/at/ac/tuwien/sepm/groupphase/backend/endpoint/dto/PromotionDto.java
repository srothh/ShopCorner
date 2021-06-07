package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PromotionDto {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private double discount;
    private LocalDateTime creationDate;
    @NotNull
    private LocalDateTime expirationDate;
    @NotNull
    @NotBlank
    private String code;
    @NotNull
    @Min(0)
    private double minimumOrderValue;

    public PromotionDto() {
    }

    public PromotionDto(Long id, String name, double discount, LocalDateTime creationDate, LocalDateTime expirationDate, String code, double minimumOrderValue) {
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
