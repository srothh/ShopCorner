package at.ac.tuwien.sepm.groupphase.backend.endpoint;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @CrossOrigin(origins = "http://localhost:8080")
    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add a new product to cart")
    public ResponseEntity<List<ProductDto>> addProductToCart(@CookieValue(name = "sessionId", defaultValue = "default") String sessionId, @RequestBody List<ProductDto> productDtos) {
        LOGGER.info("POST api/v1/carts {}", productDtos);
        List<Product> cart;
        System.out.println(sessionId);
        if (sessionId.equals("default") || cartMap.get(sessionId) == null) {
            sessionId = UUID.randomUUID().toString();
            cart = productMapper.dtoToEntity(productDtos);
            cartMap.put(sessionId, cart);
        } else {
            cart = productMapper.dtoToEntity(productDtos);
            cartMap.put(sessionId, cart);
        }
        List<ProductDto> responseBody = productMapper.entityToDto(cart);
        ResponseCookie.ResponseCookieBuilder responseCookieBuilder = ResponseCookie.from("sessionId", sessionId);
        return new ResponseEntity<>(responseBody, this.generateHeader(responseCookieBuilder.build()), HttpStatus.CREATED);
    }

    /**
     * generates response header for the create and get cart with cookie including sessionId.
     *
     * @return header for response
     */
    private HttpHeaders generateHeader(ResponseCookie responseCookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("http://localhost:4200");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, responseCookie.toString());
        headers.setOrigin("http://localhost:8080");
        return headers;
    }

}
