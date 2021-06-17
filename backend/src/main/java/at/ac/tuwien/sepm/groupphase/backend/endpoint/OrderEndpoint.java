package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaginationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public OrderDto placeNewOrder(@Valid @RequestBody OrderDto orderDto, @CookieValue(name = "sessionId", defaultValue = "default") String sessionId) {
        LOGGER.info("POST " + BASE_URL);
        LOGGER.info(orderDto.getId().toString());
        return orderMapper.orderToOrderDto(orderService.placeNewOrder(orderMapper.orderDtoToOrder(orderDto), sessionId));
    }

    /**
     * Retrieves a page of orders from the database.
     *
     * @return A list of all the retrieved orders
     */
    @Secured("ROLE_ADMIN")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all orders", security = @SecurityRequirement(name = "apiKey"))
    public PaginationDto<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "page_count", defaultValue = "15") Integer pageCount
    ) {
        LOGGER.info("GET api/v1/orders?page={}&page_count={}", page, pageCount);
        Page<Order> orderPage = orderService.getAllOrders(page, pageCount);
        return new PaginationDto<>(orderMapper.orderListToOrderDtoList(orderPage.getContent()), page, pageCount, orderPage.getTotalPages(), orderService.getOrderCount());
    }



}
