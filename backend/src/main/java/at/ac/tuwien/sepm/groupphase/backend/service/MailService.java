package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.io.IOException;

/**
 * Service that handles E-Mails.
 */
public interface MailService {

    /**
     * Sends an order confirmation email to customer.
     *
     * @param order for which confirmation should be sent
     * @param cancellationPeriod of shop
     * @throws ServiceException if something goes wrong while creating E-Mail
     */
    void sendMail(Order order, CancellationPeriod cancellationPeriod) throws IOException;
}
