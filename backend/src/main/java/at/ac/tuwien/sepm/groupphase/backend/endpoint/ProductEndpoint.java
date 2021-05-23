package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler.GlobalExceptionHandler;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductEndpoint {
    private static final String BASE_URL = "/api/v1/products";
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
     * @param categoryId optional Id of a category entity that associates a product to a category
     * @param taxRateId  Id of a tax-rate entity that associates a product to a specific tax-rate
     * @return the newly added product in a dto - format
     */
    @PermitAll
    @PostMapping({BASE_URL + "/categories/{categoryId}/tax-rates/{taxRateId}", BASE_URL + "/categories/tax-rates/{taxRateId}"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new product with a given optional category and tax-rate")
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto,
                                    @PathVariable Optional<Long> categoryId,
                                    @PathVariable Long taxRateId) {
        LOGGER.info("POST newProduct({}) " + BASE_URL, productDto);
        Long validCategoryId = null;
        if (categoryId.isPresent()) {
            validCategoryId = categoryId.get();
        }
        return this.productMapper
            .entityToDto(this.productService.createProduct(this.productMapper.dtoToEntity(productDto), validCategoryId, taxRateId));

    }

    /**
     * gets all Products form the database.
     *
     * @return all products with all given fields in a dto - format
     */

    @PermitAll
    @GetMapping(BASE_URL)
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
    @GetMapping(BASE_URL + "/simple")
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
    @PutMapping(value = {BASE_URL + "/{productId}/" + "/categories/{categoryId}/tax-rates/{taxRateId}", BASE_URL + "/{productId}/" + "/categories/tax-rates/{taxRateId}"})
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long productId,
                              @RequestBody @Valid ProductDto productDto,
                              @PathVariable Optional<Long> categoryId,
                              @PathVariable Long taxRateId) {
        LOGGER.info("PUT Product{}" + BASE_URL, productDto);
        Long validCategoryId = null;
        if (categoryId.isPresent()) {
            validCategoryId = categoryId.get();
        }
        this.productService.updateProduct(productId, this.productMapper.dtoToEntity(productDto), validCategoryId, taxRateId);

    }

    /**
     * gets a specific product with the given id.
     *
     * @param productId the id to search in the database and retrieve the associated product entity
     * @return the product entity with the associated Id
     */
    @PermitAll
    @GetMapping(BASE_URL + "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Long productId) {
        LOGGER.info("GET Product with id{}" + BASE_URL, productId);
        return this.productMapper.entityToDto(this.productService.getProductById(productId));
    }


}
