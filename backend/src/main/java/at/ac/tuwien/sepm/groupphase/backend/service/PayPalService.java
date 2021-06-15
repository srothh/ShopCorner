package at.ac.tuwien.sepm.groupphase.backend.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.http.HttpServletRequest;

/**
 * A Service class handling business logic for PayPal specific tasks.
 */
public interface PayPalService {

    /**
     * Initiates a Payment transaction with PayPal's API.
     *
     * @param price the price the payer has to pay
     *
     * @return a redirect URL to confirm the payment
     */
    public String createPayment(Double price) throws PayPalRESTException;

    /**
     * Confirms a payment with the specified PayerID and PaymentID in the request.
     *
     * @param request the request contains the necessary parameters to confirm the payment
     *
     * @return the created Payment object
     */
    public Payment confirmPayment(HttpServletRequest request) throws PayPalRESTException;
}
