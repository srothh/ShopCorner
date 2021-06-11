package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartItemRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CartRepositoryTest implements TestData {
    private final Cart cart = new Cart();
    private final CartItem cartItem = new CartItem();

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @BeforeEach
    public void beforeEach() {
        this.cartItemRepository.deleteAll();
        this.cartRepository.deleteAll();

        cart.setCreatedAt(LocalDateTime.now());
        Set<CartItem> itemSet = new HashSet<>();

        cartItem.setCart(cart);
        cartItem.setProductId(1L);
        cartItem.setQuantity(5);
        itemSet.add(cartItem);
        cart.setItems(itemSet);
    }

    @Test
    public void givenAllProperties_whenSaveCart_thenFindWithCartBySessionIdCeckIfCarExistsDeleteCarByDate() {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

           assertEquals(cart , cartRepository.findBySessionId(sessionId));
           cartRepository.deleteCartByCreatedAtIsBefore(LocalDateTime.now());
           assertEquals(false , cartRepository.existsCartBySessionId(sessionId));

    }
}
