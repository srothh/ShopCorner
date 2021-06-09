package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CartMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cartItems", target = "cartItems")
    Cart cartDtoToCart(CartDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cartItems", target = "cartItems")
    CartDto cartToCartDto(Cart dto);

}
