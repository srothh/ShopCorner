package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartDto {

    Long id;

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
    public void setCartItems(HashMap<Long, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
        return Objects.equals(cartItems, cartDto.cartItems) && Objects.equals(id, cartDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartItems);
    }

    @Override
    public String toString() {
        return "CartDto{" +
            "id=" + id +
            ", cartItems=" + cartItems +
            '}';
    }
}


