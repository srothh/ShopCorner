package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.OrderServiceImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest implements TestData {

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    Order order;
    @Spy
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private CancellationPeriod cancellationPeriod;
    private final UUID sessionID = UUID.randomUUID();
    @Mock
    private Page<Order> orders;

    @Test
    void placeNewOrderWithInvoice_thenReturnOrder() throws IOException {
        doReturn(order).when(orderService).placeNewOrder(order, sessionID.toString());
    }

    @Test
    void setCancellationPeriod_thenReturnCancellationPeriod() throws IOException {
        doReturn(cancellationPeriod).when(orderService).setCancellationPeriod(cancellationPeriod);
    }

    @Test
    void whenPlaceNewOrderWithInvalidSessionId_thenThrowServiceException() throws IOException {
        doThrow(ServiceException.class).when(orderService).placeNewOrder(order, "default");
    }

    @Test
    void whenGetAllOrdersWithNoOrdersPersisted_thenReturnEmptyPage() {
        doReturn(orders).when(orderService).getAllOrders(0,10);
    }

}
