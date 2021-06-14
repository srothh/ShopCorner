package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(OrderEndpoint.BASE_URL)
public class OrderEndpoint {
    static final String BASE_URL = "api/v1/orders";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @Autowired
    public OrderEndpoint(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    /**
     * Place a new Order.
     *
     * @param orderDto the order to be saved
     * @return saved order
     */
    @PostMapping
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place a new order", security = @SecurityRequirement(name = "apiKey"))
    public OrderDto placeNewOrder(@Valid @RequestBody OrderDto orderDto) {
        LOGGER.info("POST" + BASE_URL + "/{}", orderDto);
        return orderMapper.orderToOrderDto(orderService.placeNewOrder(orderMapper.orderDtoToOrder(orderDto)));
    }


}