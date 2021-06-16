package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    /**
     * Saves the specified order.
     *
     * @param order to be saved
     * @return the order that has just been saved.
     * @throws RuntimeException upon encountering errors with the database
     */
    Order placeNewOrder(Order order, String session);

    /**
     * Retrieves a PaginationDto containing a Page of orders from the database.
     *
     * @param page      The number of the page to retrieve
     * @param pageCount The size of the page to retrieve
     * @return A PaginationDto containing data about the retrieved items
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Order> getAllOrders(int page, int pageCount);

    /**
     * Returns amount of orders in the database.
     *
     * @return The amount of orders in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    long getOrderCount();
}
