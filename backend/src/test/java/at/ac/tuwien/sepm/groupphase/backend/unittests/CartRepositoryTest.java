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

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        cartItem.setId(1L);
        cartItem.setProductId(1L);
        cartItem.setQuantity(5);
        itemSet.add(cartItemRepository.save(cartItem));
        cart.setItems(itemSet);
    }

    @Test
    public void givenAllProperties_whenSaveCart_thenFindWithCartBySessionIdCheckIfCarExistsDeleteCarByDate() {
        UUID sessionId = UUID.randomUUID();
        cart.setSessionId(sessionId);
        cartRepository.save(cart);

           assertEquals(cart , cartRepository.findBySessionId(sessionId).get());
           assertEquals(true , cartRepository.existsCartBySessionId(sessionId));
           assertEquals( cartItem , cartRepository.findCartItemInCartBySessionId(sessionId,1L).get());
           cartRepository.deleteCartByCreatedAtIsBefore(LocalDateTime.now());
           assertEquals(false , cartRepository.existsCartBySessionId(sessionId));

    }
}
