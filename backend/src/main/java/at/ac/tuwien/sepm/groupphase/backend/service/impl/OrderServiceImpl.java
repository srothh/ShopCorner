package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CartService;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final CartService cartService;
    private final Properties properties = new Properties();

    private final String cancellationKey = "cancellationPeriod";
    private final String configPath = "src/main/resources/order.settings";
    private final MailService mailService;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            InvoiceService invoiceService,
                            CartService cartService,
                            MailService mailService,
                            ProductService productService) {
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
        this.cartService = cartService;
        this.mailService = mailService;
        this.productService = productService;
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "orderPages", allEntries = true),
        @CacheEvict(value = "counts", key = "'orders'")
    })
    public Order placeNewOrder(Order order, String session) throws IOException {
        LOGGER.trace("placeNewOrder({},{})", order, session);
        if (session.equals("default") || !this.cartService.validateSession(UUID.fromString(session))) {
            throw new ServiceException("Ungültige Sitzung!");
        }
        order.setInvoice(this.invoiceService.createInvoice(order.getInvoice()));
        order.getInvoice().setOrderNumber(Long.toHexString(order.getInvoice().getId()));
        mailService.sendMail(order, getCancellationPeriod());
        updateProductsInOrder(order);
        return orderRepository.save(order);
    }

    @Transactional
    public void updateProductsInOrder(Order order) {
        for (InvoiceItem p : order.getInvoice().getItems()) {
            Product product = productService.findById(p.getProduct().getId());
            product.setSaleCount(product.getSaleCount() + p.getNumberOfItems());
        }
    }

    @Override
    public List<Order> findAllOrders() {
        LOGGER.trace("findAllOrders()");
        return this.orderRepository.findAll();
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
        Pageable returnPage = PageRequest.of(page, pageCount, Sort.by(Sort.Direction.DESC, "invoice.date"));
        return orderRepository.findAll(returnPage);
    }

    @Override
    public Order findOrderById(Long id) {
        LOGGER.trace("findOrderById({})", id);
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find order"));
    }

    @Override
    public Order findOrderByOrderNumber(String orderNumber) {
        return this.orderRepository.findOrderByInvoice(this.invoiceService.findOneByOrderNumber(orderNumber))
            .orElseThrow(() -> new NotFoundException("Konnte die Bestellung nicht finden."));
    }

    @Override
    @Cacheable(value = "orderPages")
    public Page<Order> getAllOrdersByCustomer(int page, int pageCount, Long customerId) {
        LOGGER.trace("getAllOrdersByCustomerId({})", customerId);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return orderRepository.findAllByCustomerId(returnPage, customerId);
    }

    @Override
    @Cacheable(value = "counts", key = "'orders'")
    public long getOrderCount() {
        return orderRepository.count();
    }


    @Override
    public Order getOrderByInvoice(Invoice invoice) {
        return this.orderRepository.findOrderByInvoice(invoice).orElseThrow(() -> new NotFoundException("Bestellung konnte nicht gefunden werden"));
    }

    @Override
    public CancellationPeriod setCancellationPeriod(CancellationPeriod cancellationPeriod) throws IOException {
        LOGGER.trace("setCancellationPeriod({})", cancellationPeriod);
        properties.setProperty(cancellationKey, String.valueOf(cancellationPeriod.getDays()));

        File f = new File(configPath);
        OutputStream out = new FileOutputStream(f);
        properties.store(out, "OrderSettings");
        out.close();
        return cancellationPeriod;
    }

    @Override
    public CancellationPeriod getCancellationPeriod() throws IOException {
        LOGGER.trace("getCancellationPeriod()");
        File f = new File(configPath);
        InputStream in;
        try {
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            setCancellationPeriod(new CancellationPeriod(0));
            in = new FileInputStream(f);
        }
        properties.load(in);
        int days = Integer.parseInt(properties.getProperty(cancellationKey, "0"));
        in.close();
        return new CancellationPeriod(days);
    }


    @Caching(evict = {
        @CacheEvict(value = "orderPages", allEntries = true),
        @CacheEvict(value = "counts", key = "'orders'")
    })
    @Override
    public Order setInvoiceCanceled(Order order) {
        LOGGER.trace("setInvoiceCanceled({})", order);
        Invoice canceledInvoice = this.invoiceService.setInvoiceCanceled(order.getInvoice());
        order.setInvoice(canceledInvoice);
        return this.orderRepository.save(order);
    }
}
