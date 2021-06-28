package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.CategoryMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(CategoryEndpoint.BASE_URL)
public class CategoryEndpoint {
    static final String BASE_URL = "/api/v1/categories";
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
    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new category that is associated to a product with a given name", security = @SecurityRequirement(name = "apiKey"))
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        LOGGER.info("POST newCategory{}" + BASE_URL, categoryDto);
        return this.categoryMapper
            .entityToDto(this.categoryService.createCategory(this.categoryMapper.dtoToEntity(categoryDto)));
    }

    /**
     * Gets all categories that are currently saved in the database in a paginated manner specified by the current page and the pageCount.
     *
     * @param paginationRequestDto describes the pagination request
     *
     * @return all categories specified by the current page and the pageCount
     */
    @PermitAll
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns pages of categories", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<CategoryDto> getAllCategoriesPerPage(@Valid PaginationRequestDto paginationRequestDto) {
        LOGGER.info("GET" + BASE_URL);
        int page = paginationRequestDto.getPage();
        int pageCount = paginationRequestDto.getPageCount();
        Page<Category> categoryPage = this.categoryService.getAllCategoriesPerPage(page, pageCount);
        return new PaginationDto<>(categoryPage.getContent()
            .stream()
            .map(this.categoryMapper::entityToDto)
            .collect(Collectors.toList()), page, pageCount, categoryPage.getTotalPages(), categoryService.getCategoriesCount());
    }

    /**
     * Gets all the categories that were previously saved.
     *
     * @return all categories that were previously added
     *
     * */
    @PermitAll
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all categories relating to products that are currently stored in the database", security = @SecurityRequirement(name = "apiKey"))
    public List<CategoryDto> getAllCategories() {
        LOGGER.info("GET" + BASE_URL);
        return this.categoryService.getAllCategories()
            .stream()
            .map(this.categoryMapper::entityToDto)
            .collect(Collectors.toList());
    }

    /**
     * Updates an already existing category entity in the database.
     *
     * @param categoryId the Id of the category to execute the update
     * @param categoryDto the categoryDto with updated fields
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an already existing category entity in the database", security = @SecurityRequirement(name = "apiKey"))
    public void updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        LOGGER.info("PUT category{}" + BASE_URL, categoryDto);
        categoryService.updateCategory(categoryId, categoryMapper.dtoToEntity(categoryDto));
    }

    /**
     * Deletes a category entity in the database with the given id.
     *
     * @param categoryId the Id of the category to execute the delete action
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletes a category entity in the database", security = @SecurityRequirement(name = "apiKey"))
    public void deleteCategory(@PathVariable Long categoryId) {
        LOGGER.info("DELETE category with Id{}" + BASE_URL, categoryId);
        categoryService.deleteCategory(categoryId);
    }

    /**
     * Gets a specific category with the given id.
     *
     * @param categoryId the id of the category to retrieve from the database
     *
     * @return the requested category with the given id
     *
     */
    @PermitAll
    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets the category specified by the id", security = @SecurityRequirement(name = "apikKey"))
    public Category getCategoryById(@PathVariable Long categoryId) {
        return this.categoryService.getCategoryById(categoryId);
    }


}
