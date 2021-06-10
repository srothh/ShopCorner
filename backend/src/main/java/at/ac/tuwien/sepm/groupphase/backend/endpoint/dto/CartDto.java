package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;


public class CartDto {

    private Long id;

    @NotNull
    private CartItemDto cartItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartItemDto getCartItems() {
        return cartItem;
    }

    public void setCartItemsList(CartItemDto cartItems) {
        this.cartItem = cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) && Objects.equals(cartItem, cartDto.cartItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartItem);
    }
}


