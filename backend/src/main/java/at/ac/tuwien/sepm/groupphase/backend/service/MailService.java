package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

/**
 * Service that handles E-Mails
 */
public interface MailService {

    /**
     * Sends an order confirmation email to customer
     *
     * @param order for which confirmation should be sent
     */
    void sendMail(Order order);
}
