package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.exceptionhandler.GlobalExceptionHandler;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PermitAll
    @PostMapping({BASE_URL + "/categories/{categoryId}/tax-rates/{taxRateId}", BASE_URL + "categories/tax-rates/{taxRateId}"})
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

}
