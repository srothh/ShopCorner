package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorPermissionChangeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OperatorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(OperatorEndpoint.BASE_URL)
public class OperatorEndpoint {

    static final String BASE_URL = "api/v1/operators";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OperatorMapper operatorMapper;
    private final OperatorService operatorService;

    @Autowired
    public OperatorEndpoint(OperatorMapper operatorMapper, OperatorService operatorService) {
        this.operatorMapper = operatorMapper;
        this.operatorService = operatorService;
    }

    /**
     * Get all operators with certain permission for certain page.
     *
     * @param page which should be fetched
     * @param pageCount amount per page
     * @param permissions of needed operators
     * @return List with all needed operators
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping
    @Operation(summary = "Get list of operators", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<OverviewOperatorDto> getPage(@RequestParam("page") int page, @RequestParam("page_count") int pageCount, @RequestParam("permissions") Permissions permissions) {
        LOGGER.info("GET " + BASE_URL + "?{}&{}&{}", page, pageCount, permissions);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.toString().equals("[ROLE_EMPLOYEE]") && permissions == Permissions.admin) {
            throw new AccessDeniedException("Employee can not access admins");
        }
        PaginationDto<OverviewOperatorDto> dto;
        Page<Operator> operatorPage = operatorService.findAll(page, pageCount, permissions);
        if (permissions == Permissions.admin) {
            dto =
                new PaginationDto<>(operatorMapper.operatorToOverviewOperatorDto(operatorPage.getContent()), page, pageCount, operatorPage.getTotalPages(), operatorService.getAdminCount());
        } else {
            dto =
                new PaginationDto<>(operatorMapper.operatorToOverviewOperatorDto(operatorPage.getContent()), page, pageCount, operatorPage.getTotalPages(), operatorService.getEmployeeCount());
        }
        return dto;
    }

    /**
     * Get the operator with the given loginName.
     *
     * @param loginName of operator to be fetched
     * @return the specified operator
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping(value = "/{loginName}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Fetch the specified operator from the backend", security = @SecurityRequirement(name = "apiKey"))
    public OperatorDto getByLoginName(@PathVariable("loginName") String loginName) {
        LOGGER.info("GET " + BASE_URL + "/{}", loginName);
        OperatorDto operatorDto = operatorMapper.entityToDto(operatorService.findOperatorByLoginName(loginName));
        operatorDto.setPassword(null);
        return operatorDto;
    }

    /**
     * Save a new Operator.
     *
     * @param newOperator Operator that should be saved
     * @return saved operator
     */
    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new operator account", security = @SecurityRequirement(name = "apiKey"))
    public OperatorDto registerOperator(@Valid @RequestBody OperatorDto newOperator) {
        LOGGER.info("POST " + BASE_URL + " body: {}", newOperator);
        Operator operator = operatorMapper.dtoToEntity(newOperator);
        OperatorDto result = operatorMapper.entityToDto(operatorService.save(operator));
        result.setPassword(null);
        return result;

    }


    /**
     * Update an already existing operator.
     *
     * @param operatorDto operator to be updated
     * @return updated operator
     */
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit an existing operator account", security = @SecurityRequirement(name = "apiKey"))
    public OperatorDto editOperator(@PathVariable("id") Long id, @Valid @RequestBody OperatorDto operatorDto, Principal principal) {
        LOGGER.info("PUT " + BASE_URL + "/{}", id);

        if (operatorService.findOperatorByLoginName(principal.getName()).getId().equals(id) && id.equals(operatorDto.getId())) {

            Operator operator = operatorMapper.dtoToEntity(operatorDto);
            OperatorDto result = operatorMapper.entityToDto(operatorService.update(operator));
            result.setPassword(null);
            return result;
        }

        throw new AccessDeniedException("Illegal access");
    }

    /**
     * Deletes an employee with given id.
     *
     * @param id of employee that should be deleted
     */
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("DELETE " + BASE_URL + "/{}", id);
        operatorService.delete(id);
    }

    /**
     * Changes Permissions of the operator with the given id.
     *
     * @param id of operator that needs new permissions
     * @param operatorPermissionChangeDto Dto with permission that should be updated
     */
    @Secured({"ROLE_ADMIN"})
    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePermissions(@PathVariable("id") Long id, @Valid @RequestBody OperatorPermissionChangeDto operatorPermissionChangeDto, Principal principal) {
        LOGGER.info("PATCH " + BASE_URL + "/{}: {}", id, operatorPermissionChangeDto);

        if (operatorService.findOperatorByLoginName(principal.getName()).getId().equals(id)) {
            throw new AccessDeniedException("Admins cannot change their own permissions");
        }
        operatorService.changePermissions(id, operatorPermissionChangeDto.getPermissions());
    }
}
