package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CustomerMapper {
    @Named("Customer")
    Customer CustomerDtoToCustomer(CustomerRegistrationDto dto);

    CustomerRegistrationDto CustomerToCustomerDto(Customer customer);
}
