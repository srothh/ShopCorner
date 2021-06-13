package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("api/v1/address")
public class AddressEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    @Autowired
    public AddressEndpoint(AddressMapper addressMapper, AddressService addressService) {
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    @PermitAll
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new address")
    public AddressDto addNewAddress(@Valid @RequestBody AddressDto dto) {
        LOGGER.info("POST api/v1/address");
        return addressMapper.addressToAddressDto(addressService.addNewAddress(addressMapper.addressDtoToAddress(dto)));
    }

    /**
     * Retrieves addresses from persistent database.
     *
     * @return all addresses from the database
     */
    //TODO Change to Secured(ROLE_ADMIN)
    @PermitAll
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "retrieve all addresses")
    public List<AddressDto> getAllAddresses() {
        LOGGER.info("GET api/v1/address");
        return addressMapper.addressListToAddressDtoList(addressService.getAllAddresses());
    }
}
