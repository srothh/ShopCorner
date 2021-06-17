package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            InvoiceService invoiceService,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "orderPages", allEntries = true),
        @CacheEvict(value = "counts", key = "'orders'")
    })
    public Order placeNewOrder(Order order, String session) {
        LOGGER.info("placeNewOrder({})", order);
        if (session.equals("default") || !this.cartService.validateSession(UUID.fromString(session))) {
            throw new ServiceException("invalid sessionId");
        }
        order.setInvoice(this.invoiceService.createInvoice(order.getInvoice()));
        return orderRepository.save(order);
    }

    @Override
    @Cacheable(value = "orderPages")
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
    @Cacheable(value = "counts", key = "'orders'")
    public long getOrderCount() {
        return orderRepository.count();
    }


    @Override
    public Order getOrderByInvoice(Invoice invoice) {
        return this.orderRepository.findOrderByInvoice(invoice).orElseThrow(() -> new NotFoundException("Could not find order"));
    }

}
