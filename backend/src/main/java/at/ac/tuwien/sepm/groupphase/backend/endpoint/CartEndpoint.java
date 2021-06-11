package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartItemMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.CartItem;
import at.ac.tuwien.sepm.groupphase.backend.service.CartItemService;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/carts")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CartMapper cartMapper;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Autowired
    public CartEndpoint(CartMapper cartMapper, CartService cartService, CartItemMapper cartItemMapper, CartItemService cartItemService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
        this.cartItemMapper = cartItemMapper;
        this.cartItemService = cartItemService;
    }

    @PermitAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update the cart")
    public CartDto getCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, HttpServletResponse response) {
        Cart cart;
        if (sessionId.equals("default") || !this.sessionExists(sessionId) || !this.validateSession(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            this.createCookie(sessionId, response);
        }
        if (!this.sessionExists(sessionId)) {
            cart = this.cartService.createCart(new Cart(UUID.fromString(sessionId),LocalDateTime.now()));
            return this.cartMapper.cartToCartDto(cart);
        }
        cart = this.cartService.findCartBySessionId(UUID.fromString(sessionId));
        CartDto cartDto  = this.cartMapper.cartToCartDto(cart);
        cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));
        return cartDto;
    }

    @PermitAll
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "update the cart")
    public CartDto updateProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @Valid @RequestBody CartItemDto item, HttpServletResponse response) {
        UUID sessionUuid = UUID.fromString(sessionId);
        Cart cart = this.cartService.findCartBySessionId(sessionUuid);
        this.cartItemService.updateCartItem(cart, this.cartItemMapper.cartItemDtoToCartItem(item));
        cart = this.cartService.findCartBySessionId(sessionUuid);
        CartDto cartDto  = this.cartMapper.cartToCartDto(cart);
        cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));
        return cartDto;
    }


    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "update the cart")
    public CartDto createProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @Valid @RequestBody CartItemDto item, HttpServletResponse response) {
        CartDto cartDto = new CartDto();
        if (sessionId.equals("default") || !this.sessionExists(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            this.createCookie(sessionId, response);
            Cart cart = this.creatCartAddCartItem(sessionId, item);
            cartDto = this.cartMapper.cartToCartDto(cart);
            cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(cart.getItems()));

        } else if (this.validateSession(sessionId) && this.sessionExists(sessionId)) {
            System.out.println("add");
            Cart createdCart = this.addCartItem(sessionId, item);
            cartDto = this.cartMapper.cartToCartDto(createdCart);
            cartDto.setCartItems(this.cartItemMapper.cartItemToCartItemDto(createdCart.getItems()));
        }
        return cartDto;
    }


    private Cart addCartItem(String sessionId, CartItemDto item) {
        UUID sessionUuid = UUID.fromString(sessionId);
        Cart cart = this.cartService.findCartBySessionId(sessionUuid);
        Set<CartItem> itemSet = cart.getItems();
        cart.setItems(null);
        CartItem cartItem = this.cartItemMapper.cartItemDtoToCartItem(item);
        cartItem.setCart(cart);
        itemSet.add(cartItem);
        cart.setItems(itemSet);
        Cart updatedCart = this.cartService.addItemToCart(cart);
        return updatedCart;
    }


    private Cart creatCartAddCartItem(String sessionId, CartItemDto item) {
        UUID sessionUuid = UUID.fromString(sessionId);
        Cart cart = new Cart();

        Set<CartItem> itemSet = cart.getItems();
        itemSet.add(this.cartItemMapper.cartItemDtoToCartItem(item));
        cart.setItems(itemSet);
        cart.setSessionId(sessionUuid);

        Cart createdCart = this.cartService.createCart(cart);
        return createdCart;
    }


    private void createCookie(String sessionId, HttpServletResponse response) {
        ResponseCookie resCookie = ResponseCookie.from("sessionId", sessionId)
            .httpOnly(true)
            .sameSite("None")
            .secure(true)
            .build();
        response.addHeader("Set-Cookie", resCookie.toString());
    }


    private boolean validateSession(String sessionId) {
        return sessionId.matches("([A-Za-z0-9_-]*){36}$");
    }


    private boolean sessionExists(String sessionId) {
        return this.cartService.sessionIdExists(UUID.fromString(sessionId));
    }

}
