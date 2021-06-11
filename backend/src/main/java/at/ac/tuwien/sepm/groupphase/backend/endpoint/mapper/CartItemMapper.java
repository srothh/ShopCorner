package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper
@Component
public interface CartItemMapper {

    @Named("toDto")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);

    @IterableMapping(qualifiedByName = "toDto")
    Set<CartItemDto> cartItemToCartItemDto(Set<CartItem> cartItems);


    @Named("toEntity")
    CartItem cartItemDtoToCartItem(CartItemDto cartItem);

    @IterableMapping(qualifiedByName = "toEntity")
    Set<CartItem> cartItemDtoToCartItem(Set<CartItemDto> cartItems);
}
