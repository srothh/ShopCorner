package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CartItemMapperTest implements TestData {
    private static CartItem cartItem1 = new CartItem();
    private static CartItem cartItem2 = new CartItem();

    @Autowired
    private CartItemMapper cartItemMapper;

    @BeforeAll
    static void beforeAll() {
        cartItem1.setId(CART_ITEM_ID);
        cartItem1.setProductId(CART_ITEM_PRODUCT_ID);
        cartItem1.setQuantity(CART_ITEM_QUANTITY);

        cartItem2.setId(CART_ITEM_ID_2);
        cartItem2.setProductId(CART_ITEM_PRODUCT_ID_2);
        cartItem2.setQuantity(CART_ITEM_QUANTITY_2);
    }

    @Test
    public void givenAllProperties_whenMapCartItemDtoToEntity_thenEntityHasAllProperties() {
        CartItemDto cartItemDto = cartItemMapper.cartItemToCartItemDto(cartItem1);
        assertAll(
            () -> assertEquals(cartItem1.getProductId(), cartItemDto.getProductId()),
            () -> assertEquals(cartItem1.getQuantity(), cartItemDto.getQuantity())
        );
    }

    @Test
    public void givenAllProperties_whenMapSetWithTwoCartItemDtoToEntity_thenEntityHasAllProperties() {
        Set<CartItem> itemSet = new HashSet<>();
        itemSet.add(cartItem1);
        itemSet.add(cartItem2);
        Set<CartItemDto> cartItemDtos = cartItemMapper.cartItemToCartItemDto(itemSet);
        assertEquals(itemSet.size(), cartItemDtos.size());

        CartItemDto itemDto = (CartItemDto)cartItemDtos.toArray()[0];
        assertAll(
            () -> assertEquals(cartItem1.getProductId(), itemDto.getProductId()),
            () -> assertEquals(cartItem1.getQuantity(), itemDto.getQuantity())
        );

    }
}
