package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartItemDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/carts")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CartMapper cartMapper;
    private final CartService cartService;

    @Autowired
    public CartEndpoint(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    @PermitAll
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "update the cart")
    public ResponseEntity<CartDto> updateProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @RequestBody CartItemDto item, HttpServletResponse response) {
        if (this.validateSession(sessionId) && this.sessionExists(sessionId)) {
            Cart cart= this.cartService.findCartBySessionId(UUID.fromString(sessionId));



        } else if (sessionId.equals("default")) {
            sessionId = UUID.randomUUID().toString();
            ResponseCookie resCookie = ResponseCookie.from("sessionId", sessionId)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
            response.addHeader("Set-Cookie", resCookie.toString());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(401).build();
    }


    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "update the cart")
    public ResponseEntity<CartDto> createProductInCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @RequestBody CartDto cartDto, HttpServletResponse response) {
        if (sessionId.equals("default")) {
            sessionId = UUID.randomUUID().toString();
            ResponseCookie resCookie = ResponseCookie.from("sessionId", sessionId)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
            response.addHeader("Set-Cookie", resCookie.toString());
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(401).build();
    }




    private boolean validateSession(String sessionId) {
        return sessionId.matches("([A-Za-z0-9_-]*){36}$");
    }

    private boolean sessionExists(String sessionId) {
        return this.cartService.sessionIdExists(UUID.fromString(sessionId));
    }

}
