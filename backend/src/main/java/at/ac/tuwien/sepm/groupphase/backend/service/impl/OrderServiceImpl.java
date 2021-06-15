package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.repository.InvoiceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final Validator validator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, Validator validator,
                            InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public Order placeNewOrder(Order order) {
        LOGGER.info("placeNewOrder({})", order.getInvoiceId().getAmount());
        order.setInvoiceId(this.invoiceService.createInvoice(order.getInvoiceId()));
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrders(int page, int pageCount) {
        LOGGER.trace("getAllOrders()");
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return orderRepository.findAll(returnPage);
    }


    @Override
    public long getOrderCount() {
        return orderRepository.count();
    }
}
