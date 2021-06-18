package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.ConfirmedPayment;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.api.payments.Payment;

import javax.servlet.http.HttpServletRequest;

/**
 * A Service class handling business logic for PayPal specific tasks.
 */
public interface PayPalService {

    /**
     * Initiates a Payment transaction with PayPal's API.
     *
     * @param order the order containing all information for the payment process
     *
     * @return a redirect URL to confirm the payment
     */
    public String createPayment(Order order) throws PayPalRESTException;

    /**
     * Confirms a payment with the specified PayerID and PaymentID in the request.
     *
     * @param confirmedPayment the confirmed payment contains the necessary parameters to confirm the payment
     *
     * @return the created Payment object
     */
    public Payment confirmPayment(ConfirmedPayment confirmedPayment) throws PayPalRESTException;
}
