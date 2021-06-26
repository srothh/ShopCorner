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
     * @throws PayPalRESTException is being thrown when processing Paypal's API ends in a failure
     */
    public String createPayment(Order order) throws PayPalRESTException;

    /**
     * Confirms a payment with the specified PayerID and PaymentID in the request.
     *
     * @param confirmedPayment the confirmed payment contains the necessary parameters to confirm the payment
     *
     *
     * @return the created Payment object
     * @throws PayPalRESTException is being thrown when processing Paypal's API ends in a failure
     */
    public Payment confirmPayment(ConfirmedPayment confirmedPayment) throws PayPalRESTException;

    /**
     * Gets a specific ConfirmedPayment specified by paymentId and payerId.
     *
     * @param paymentId the paymentId to look for in a ConfirmedPayment
     * @param payerId the payerId to look for in a ConfirmedPayment
     *
     * @return the created Payment object
     */
    public ConfirmedPayment getConfirmedPaymentByPaymentIdAndPayerId(String paymentId, String payerId);
}
