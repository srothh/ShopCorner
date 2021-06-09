package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CartDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost", allowCredentials = "true")
@RestController
@RequestMapping("api/v1/carts")
public class CartEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductMapper productMapper;
    private final ProductService productService;
    private HashMap<String, List<Product>> cartMap = new HashMap();

    @Autowired
    public CartEndpoint(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PermitAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Generate session", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity setSessionId(HttpServletResponse response) {
        ResponseCookie resCookie = ResponseCookie.from("sessionId",UUID.randomUUID().toString())
            .httpOnly(true)
            .sameSite("None")
            .secure(true)
            .path("/")
            .build();
        response.addHeader("Set-Cookie", resCookie.toString());
        return ResponseEntity.ok("request succeeded");
    }


    @PermitAll
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add a new product to cart", security = @SecurityRequirement(name = "apiKey"))
    public CartDto addProductToCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @RequestBody List<ProductDto> productDtos, HttpServletRequest httpServletRequest) {
        LOGGER.info("POST api/v1/carts {}", productDtos);
        List<Product> cart;
        System.out.println(httpServletRequest.getCookies());
        if (!sessionId.equals("default")) {
            cart = productMapper.dtoToEntity(productDtos);
            cartMap.put(sessionId, cart);
        }

        Map<Long, Integer> cartItems  = new HashMap<>();
        cartItems.put(1L, 2);
        cartItems.put(2L, 2);
        cartItems.put(3L, 2);
        return new CartDto(cartItems);
    }



}
