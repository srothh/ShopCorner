package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CartMapper {

    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapToEntity")
    Cart cartDtoToCart(CartDto dto);

    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapToDto")
    CartDto cartToCartDto(Cart dto);

    @Named("mapToDto")
    public static List<CartItemDto> toDto(Map<Long, Integer> cartItems) {
        List<CartItemDto> newItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            newItems.add(new CartItemDto(entry.getKey(), entry.getValue()));
        }

        return newItems;
    }

    @Named("mapToEntity")
    public static Map<Long, Integer> toEntity(List<CartItemDto>  cartItems) {
        Map<Long, Integer> newItems = new HashMap<>();
        for (CartItemDto entry : cartItems) {
            newItems.put(entry.getProductId(), entry.getQuantity());
        }
        return newItems;
    }

}
