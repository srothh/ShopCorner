package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import org.springframework.data.util.Pair;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartDto {

    @NotNull
    Map<Long, Integer> cartItems = new HashMap();

    public CartDto() {
    }

    public CartDto(Map<Long, Integer> cartItems) {
        this.cartItems = cartItems;
    }


    public Map<Long, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Integer> cartItems) {
        this.cartItems = cartItems;
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
        return Objects.equals(cartItems, cartDto.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems);
    }
}


