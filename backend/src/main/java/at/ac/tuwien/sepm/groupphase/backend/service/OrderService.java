package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

public interface OrderService {

    /**
     * Saves the specified order.
     *
     * @param order to be saved
     * @return the order that has just been saved.
     * @throws RuntimeException upon encountering errors with the database
     */
    Order placeNewOrder(Order order);
}
