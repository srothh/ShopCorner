package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new product with a given optional category and tax-rate", security = @SecurityRequirement(name = "apiKey"))
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        LOGGER.info("POST newProduct({}) " + BASE_URL, productDto);
        return this.productMapper
            .entityToDto(this.productService.createProduct(this.productMapper.dtoToEntity(productDto)));
    }

    /**
     * Gets all products form the database in a paginated manner.
     *
     * @param page describes the number of the page
     * @param pageCount the number of entries in each page
     * @return all products with all given fields in a dto - format and paginated specified by page and pageCount
     */
    @PermitAll
    @GetMapping(params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all products that are currently stored in the database", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<ProductDto> getAllProductsPerPage(@RequestParam("page") int page, @RequestParam("page_count") int pageCount,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "") String name,
                                                           @RequestParam(name = "category_id", required = false, defaultValue = "-1") Long categoryId) {
        LOGGER.info("GET " + BASE_URL);
        Page<Product> productPage = this.productService.getAllProductsPerPage(page, pageCount, categoryId, sortBy, name);
        Long productCount;
        if (name.isEmpty() && categoryId == -1) {
            productCount = this.productService.getProductsCount();
        } else {
            // Temporarily don't cache with filters
            productCount = productPage.getTotalElements();
        }
        return new PaginationDto<>(productPage.getContent()
            .stream()
            .map(this.productMapper::entityToDto)
            .collect(Collectors.toList()), page, pageCount, productPage.getTotalPages(), productCount);
    }

    /**
     * Gets all simple products from the database, which omits some fields like picture and category.
     *
     * @return all simple products ( product without picture,category) in a dto - format NOT PAGINATED
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/simple")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Returns all products that are currently stored in the database without picture and category",
        security = @SecurityRequirement(name = "apiKey")
    )
    public List<SimpleProductDto> getAllSimpleProducts() {
        LOGGER.info("GET" + BASE_URL + "/simple");
        return this.productService.getAllProducts()
            .stream()
            .map(this.productMapper::simpleProductEntityToDto)
            .collect(Collectors.toList());
    }

    /**
     * Updates an already existing product from the database.
     *
     * @param productId  the Id of the product to execute the udpate
     * @param productDto the product dto with the updated fields
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an already existing product from the database", security = @SecurityRequirement(name = "apiKey"))
    public void updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDto productDto) {
        LOGGER.info("PUT Product{} with Id{}" + BASE_URL, productDto, productId);
        this.productService.updateProduct(productId, this.productMapper.dtoToEntity(productDto));
    }

    /**
     * Gets a specific product with the given id.
     *
     * @param productId the id to search in the database and retrieve the associated product entity
     * @return the product entity with the associated Id
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets a specific product with the give Id", security = @SecurityRequirement(name = "apiKey"))
    public ProductDto getProductById(@PathVariable Long productId) {
        LOGGER.info("GET Product with id{}" + BASE_URL, productId);
        return this.productMapper.entityToDto(this.productService.findById(productId));
    }

    /**
     * Deletes a specific product with the given id.
     *
     * @param productId the id to search in the database and retrieve the associated product entity
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes a specific product with the given Id", security = @SecurityRequirement(name = "apiKey"))
    public void deleteProductById(@PathVariable Long productId) {
        LOGGER.info("DELETE Product with id{}" + BASE_URL, productId);
        this.productService.deleteProductById(productId);
    }

}
