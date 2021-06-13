package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component

public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto dto);
}
