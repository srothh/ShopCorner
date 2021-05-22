package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProductMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryEndpoint {
    private static final String BASE_URL = "/api/v1/categories";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryEndpoint(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Creates a new category in the database.
     *
     * @param categoryDto the category as a dto which contains the given fields to store
     *
     * @return the newly added category in a dto - format
     */
    @PermitAll
    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new category that is associated to a product with a given name")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        LOGGER.info("POST newCategory{}" + BASE_URL, categoryDto);
        return this.categoryMapper
            .entityToDto(this.categoryService.createCategory(this.categoryMapper.dtoToEntity(categoryDto)));
    }

    /**
     * Gets all categories that are currently saved in the database.
     *
     * @return all categories in dto format
     */
    @PermitAll
    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all categories relating to products that are currently stored in the database")
    public List<CategoryDto> getAllCategories() {
        LOGGER.info("GET" + BASE_URL);
        return this.categoryService.getAllCategories()
            .stream()
            .map(this.categoryMapper::entityToDto)
            .collect(Collectors.toList());
    }
}
