package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CartItemDto {

    Long id;

    @NotNull(message = "ProductId darf nicht null sein")
    private Long productId;

    @Min(value = 1, message = "Die minimale Anzahl ist 1")
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemDto that = (CartItemDto) o;
        return quantity == that.quantity && Objects.equals(productId, that.productId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, quantity);
    }


}
