package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/carts")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CartMapper cartMapper;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;

    @Autowired
    public CartEndpoint(CartMapper cartMapper, CartService cartService, CartItemMapper cartItemMapper) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
        this.cartItemMapper = cartItemMapper;
    }

    /**
     * Get cart assigned by sessionId.
     *
     * @return CartDto with all given information of the Cart
     */
    @PermitAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update the cart")
    public CartDto getCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, HttpServletResponse response) {
        LOGGER.info("GET /api/v1/carts/");
        Cart cart;

        if (sessionId.equals("default") || !this.cartService.validateSession(UUID.fromString(sessionId))) {
            UUID sessionUuid = UUID.randomUUID();
            this.createCookie(sessionUuid, response);

            if (!this.cartService.sessionIdExists(sessionUuid)) {
                cart = this.cartService.createEmptyCart(sessionUuid);
                return this.cartMapper.cartToCartDto(cart);
            }
        }

        cart = this.cartService.findCartBySessionId(UUID.fromString(sessionId));
        CartDto cartDto = this.cartMapper.cartToCartDto(cart);
        cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));
        return cartDto;
    }

    /**
     * Delete cartItem from cart by productId.
     *
     * @param id the productId of the cartItem to be removed
     */
    @PermitAll
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update the cart")
    public ResponseEntity deleteCartItem(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @PathVariable Long id) {
        LOGGER.info("DELETE /api/v1/carts/{}:id", id);
        if (sessionId.equals("default")) {
            return ResponseEntity.badRequest().build();
        }
        if (this.cartService.sessionIdExists(UUID.fromString(sessionId))) {
            this.cartService.deleteCartItemById(id);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Update the cartItem from cart assigned by sessionId.
     *
     * @param item the cartItem to be updated
     * @return CartDto with all given information of the Cart
     */
    @PermitAll
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update the cart")
    public ResponseEntity<CartDto> updateProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @Valid @RequestBody CartItemDto item, HttpServletResponse response) {
        LOGGER.info("PUT /api/v1/carts/ {}", item);
        if (!sessionId.equals("default")) {
            if (this.cartService.sessionIdExists(UUID.fromString(sessionId))) {
                UUID sessionUuid = UUID.fromString(sessionId);
                Cart cart = this.cartService.findCartBySessionId(sessionUuid);
                cart = this.cartService.updateCart(cart, this.cartItemMapper.cartItemDtoToCartItem(item));
                CartDto cartDto = this.cartMapper.cartToCartDto(cart);
                cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));
                return ResponseEntity.ok(cartDto);
            }
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * creates a cart and a cartItem which are assigned to a sessionId.
     *
     * @param item the cartItem to be created
     * @return CartDto with all given information of the Cart
     */
    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "update the cart")
    public CartDto createProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @Valid @RequestBody CartItemDto item, HttpServletResponse response) {
        LOGGER.info("POST /api/v1/carts/ {}", item);
        CartDto cartDto = new CartDto();
        if (sessionId.equals("default") || !this.cartService.sessionIdExists(UUID.fromString(sessionId))) {
            UUID sessionUuid = UUID.randomUUID();
            this.createCookie(sessionUuid, response);
            Cart cart = this.cartService.addCartItemToNewCart(sessionUuid, this.cartItemMapper.cartItemDtoToCartItem(item));
            cartDto = this.cartMapper.cartToCartDto(cart);
            cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));

        } else if (this.cartService.validateSession(UUID.fromString(sessionId))) {
            Cart createdCart = this.cartService.addCartItemToCart(UUID.fromString(sessionId), this.cartItemMapper.cartItemDtoToCartItem(item));
            cartDto = this.cartMapper.cartToCartDto(createdCart);
            cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(createdCart.getItems()));
        }
        return cartDto;
    }

    /**
     * Creates a cookie with a generated sessionId.
     *
     * @param sessionId the generated sessionId for the session assignment
     * @param response  the response for the client
     */
    private void createCookie(UUID sessionId, HttpServletResponse response) {
        ResponseCookie resCookie = ResponseCookie.from("sessionId", sessionId.toString())
            .httpOnly(true)
            .sameSite("None")
            .secure(true)
            .build();
        response.addHeader("Set-Cookie", resCookie.toString());
    }





}
