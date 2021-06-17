package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

/**
 * Service that handles E-Mails.
 */
public interface MailService {

    /**
     * Sends an order confirmation email to customer.
     *
     * @param order for which confirmation should be sent
     * @throws ServiceException if something goes wrong while creating E-Mail
     */
    void sendMail(Order order);
}
