package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CartMapperTest implements TestData {
    private static final Cart cart = new Cart();

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @BeforeAll
    static void beforeAll() {
        CartItem cartItem = new CartItem();
        cart.setId(CART_ID);
        cart.setCreatedAt(LocalDateTime.now());

        Set<CartItem> itemSet = new HashSet<>();

        //cartItem.setCart(cart);
        cartItem.setId(CART_ITEM_ID);
        cartItem.setProductId(CART_ITEM_PRODUCT_ID);
        cartItem.setQuantity(CART_ITEM_QUANTITY);
        itemSet.add(cartItem);
        cart.setItems(itemSet);
    }

    @Test
    public void givenAllProperties_whenMapCartDtoToEntity_thenEntityHasAllProperties() {
        CartDto cartDto = this.cartMapper.cartToCartDto(cart);
        cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));
        assertAll(
            () -> assertEquals(TEST_INVOICE_ID, cartDto.getId()),
            () -> assertEquals(cart.getItems().size(), cartDto.getCartItems().size())
        );
    }

}
