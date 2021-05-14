package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AddressMapper {
    Address addressDtoToAddress(AddressDto dto);

    AddressDto addressToAddressDto(Address address);

    List<AddressDto> addressListToAddressDtoList(List<Address> addresses);
}
