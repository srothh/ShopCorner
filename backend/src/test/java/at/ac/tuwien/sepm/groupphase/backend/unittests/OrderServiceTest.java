package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest implements TestData {

    @Mock
    private OrderService orderService;
    @Mock
    Order order;
    @Mock
    private CancellationPeriod cancellationPeriod;
    UUID sessionID = UUID.randomUUID();

    @Test
    public void placeNewOrderWithInvoice_thenReturnOrder(){
        when(orderService.placeNewOrder(order, sessionID.toString())).thenReturn(order);
    }

    @Test
    public void setCancellationPeriod_thenReturnCancellationPeriod() throws IOException {
        when(orderService.setCancellationPeriod(cancellationPeriod)).thenReturn(cancellationPeriod);
    }
}
