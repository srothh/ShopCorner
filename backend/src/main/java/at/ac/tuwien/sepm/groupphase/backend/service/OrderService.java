package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;


/**
 * Service that handles Orders of Customers.
 */
public interface OrderService {

    /**
     * Saves the specified order and calls MailService to send email.
     *
     * @param order to be saved
     * @return the order that has just been saved.
     * @throws RuntimeException upon encountering errors with the database
     */
    Order placeNewOrder(Order order, String session) throws IOException;

    /**
     * Retrieves a PaginationDto containing a Page of orders from the database.
     *
     * @param page The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @return A PaginationDto containing data about the retrieved items
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Order> getAllOrders(int page, int pageCount);

    /**
     * Returns the specified order from the database.
     *
     * @param id the id of the order to be retrieved
     * @return the specified order
     * @throws NotFoundException is thrown if the specified order does not exists
     * @throws RuntimeException upon encountering errors with the database
     */
    Order findOrderById(Long id);


    /**
     * Updates the given order in the database to canceled.
     *
     * @param order is the order to be canceled
     * @return The canceled order from the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    Order setInvoiceCanceled(Order order);

    /**
     * Retrieves a PaginationDto containing a Page of orders from a specific customer from the database.
     *
     * @param page The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @param customerId the customer Id associated to an order
     * @return A PaginationDto containing data about the retrieved items
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Order> getAllOrdersByCustomer(int page, int pageCount, Long customerId);

    /**
     * Returns amount of orders in the database.
     *
     * @return The amount of orders in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    long getOrderCount();

    /**
     * Returns Order of a given invoice.
     *
     * @param invoice the invoice of the order
     * @return The saved order
     * @throws NotFoundException when no order with the invoice is found
     * @throws RuntimeException  upon encountering errors with the database
     */
    Order getOrderByInvoice(Invoice invoice);

    /**
     * Sets the cancellation period for orders.
     *
     * @param cancellationPeriod the cancellationperiod to set
     * @return the cancellationperiod as persisted
     * @throws IOException upon encountering problems with the configuration file
     */
    CancellationPeriod setCancellationPeriod(CancellationPeriod cancellationPeriod) throws IOException;

    /**
     * Returns the cancellation period for orders.
     *
     * @return the cancellation period for orders
     * @throws IOException upon encountering problems with the configuration file
     */
    CancellationPeriod getCancellationPeriod() throws IOException;

    /**
     * Increments the salescounts of sold products.
     *
     * @param order The order containing the information on the sold products
     * @throws RuntimeException upon encountering problems with the database
     */
    void updateProductsInOrder(Order order);

    /**
     * Finds all orders currently saved in the database.
     *
     *
     * @throws RuntimeException upon encountering problems with the database
     */
    List<Order> findAllOrders();


}
