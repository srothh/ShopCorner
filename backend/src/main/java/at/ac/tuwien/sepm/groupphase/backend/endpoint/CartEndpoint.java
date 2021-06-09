package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CartMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.jni.Local;
import org.hibernate.id.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/carts")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CartMapper cartMapper;
    private final CartService cartService;

    private HashMap<String, List<Product>> cartMap = new HashMap();

    @Autowired
    public CartEndpoint(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }



    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add a new product to cart")
    public ResponseEntity<CartDto> addProductToCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @RequestBody CartDto cartDto, HttpServletResponse response) {
        LOGGER.info("POST api/v1/carts");

        if (sessionId.equals("default")) {
            sessionId = UUID.randomUUID().toString();
            ResponseCookie resCookie = ResponseCookie.from("sessionId", sessionId)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
            response.addHeader("Set-Cookie", resCookie.toString());
            Cart cart = this.cartMapper.cartDtoToCart(cartDto);
            cart.setSessionId(UUID.fromString(sessionId));
            cart.setCreatedAt(LocalDateTime.now());
            Cart createdCart = this.cartService.addProductsToCart(cart);
            return ResponseEntity.ok().body(this.cartMapper.cartToCartDto(createdCart));

        } else if (this.validateSession(sessionId)) {
            System.out.println(sessionId);
            Cart cart = this.cartMapper.cartDtoToCart(cartDto);
            cart.setSessionId(UUID.fromString(sessionId));
            cart.setCreatedAt(LocalDateTime.now());
            Cart createdCart = this.cartService.addProductsToCart(cart);
            return ResponseEntity.ok().body(this.cartMapper.cartToCartDto(createdCart));
        }

        return ResponseEntity.status(401).build();

    }

    private boolean validateSession(String sessionId) {
        return sessionId.matches("([A-Za-z0-9_-]*){36}$")&&!this.cartService.sessionIdExists(UUID.fromString(sessionId));
    }

}
