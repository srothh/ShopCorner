package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ProductEndpoint.BASE_URL)
public class ProductEndpoint {
    static final String BASE_URL = "/api/v1/products";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductEndpoint(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * Adds a new Product to the database.
     *
     * @param productDto the dto class containing all necessary field
     * @return the newly added product in a dto - format
     */
    @PermitAll
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new product with a given optional category and tax-rate")
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        LOGGER.info("POST newProduct({}) " + BASE_URL, productDto);
        return this.productMapper
            .entityToDto(this.productService.createProduct(this.productMapper.dtoToEntity(productDto)));

    }

    /**
     * gets all Products form the database.
     *
     * @return all products with all given fields in a dto - format
     */
    @PermitAll
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all products that are currently stored in the database")
    public List<ProductDto> getAllProducts() {
        LOGGER.info("GET " + BASE_URL);
        return this.productService.getAllProducts()
            .stream()
            .map(this.productMapper::entityToDto)
            .collect(Collectors.toList());
    }

    /**
     * Gets all simple products from the database, which omits some fields like picture and category.
     *
     * @return all simple products ( product without picture,category) in a dto - format
     */
    @PermitAll
    @GetMapping("/simple")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all products that are currently stored in the database without picture and category")
    public List<SimpleProductDto> getAllSimpleProducts() {
        LOGGER.info("GET" + BASE_URL + "/simple");
        return this.productService.getAllProducts()
            .stream()
            .map(this.productMapper::simpleProductEntityToDto)
            .collect(Collectors.toList());
    }

    /**
     * updates an already existing product from the database.
     */

    @PermitAll
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDto productDto) {
        LOGGER.info("PUT Product{} with Id{}" + BASE_URL, productDto, productId);
        this.productService.updateProduct(productId, this.productMapper.dtoToEntity(productDto));
    }

    /**
     * gets a specific product with the given id.
     *
     * @param productId the id to search in the database and retrieve the associated product entity
     * @return the product entity with the associated Id
     */
    @PermitAll
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Long productId) {
        LOGGER.info("GET Product with id{}" + BASE_URL, productId);
        return this.productMapper.entityToDto(this.productService.findById(productId));
    }


}
