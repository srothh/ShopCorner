package at.ac.tuwien.sepm.groupphase.backend.unittests;

/*
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
*/