package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerRegistrationDto dto);

    CustomerRegistrationDto customerToCustomerRegistrationDto(Customer customer);

    CustomerDto customerToCustomerDto(Customer customer);

    List<CustomerDto> customerListToCustomerDtoList(List<Customer> customers);

}
