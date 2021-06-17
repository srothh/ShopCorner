package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

public interface MailService {

    void sendMail(Order order);
}
