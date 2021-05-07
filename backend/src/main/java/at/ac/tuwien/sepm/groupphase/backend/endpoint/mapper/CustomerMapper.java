package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface CustomerMapper {
    @Named("Customer")
    Customer CustomerDtoToCustomer(CustomerDto dto);
    CustomerDto CustomerToCustomerDto(Customer customer);
}
