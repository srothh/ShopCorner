package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
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
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest implements TestData {

    @Mock
    Order order;
    @Spy
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private CancellationPeriod cancellationPeriod;
    private UUID sessionID = UUID.randomUUID();

    @Test
    public void placeNewOrderWithInvoice_thenReturnOrder() {
        doReturn(order).when(orderService).placeNewOrder(order, sessionID.toString());
    }

    @Test
    public void setCancellationPeriod_thenReturnCancellationPeriod() throws IOException {
        doReturn(cancellationPeriod).when(orderService).setCancellationPeriod(cancellationPeriod);
    }

    @Test
    public void whenPlaceNewOrderWithInvalidSessionId_thenThrowServiceException() {
        doThrow(ServiceException.class).when(orderService).placeNewOrder(order, "default");
    }

    @Test
    public void whenGetAllOrdersWithNoOrdersPersisted_thenThrowNullPointerException() {
        doThrow(NullPointerException.class).when(orderService).getAllOrders(0,10);
    }

}
