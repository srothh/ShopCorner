package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.PayPalProperties;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.service.PayPalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalServiceImpl implements PayPalService {

    private final PayPalProperties payPalProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public PayPalServiceImpl(PayPalProperties payPalProperties) {
        this.payPalProperties = payPalProperties;
    }


    @Override
    public String createPayment(Order order) throws PayPalRESTException {
        Invoice invoice = order.getInvoice();
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(String.valueOf(invoice.getAmount()));
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/#/checkout");
        redirectUrls.setReturnUrl("http://localhost:4200/#/order-success");

        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment;
        String redirectUrl = "";
        APIContext apiContext = payPalProperties.apiContext();
        createdPayment = payment.create(apiContext);
        List<Links> links = createdPayment.getLinks();
        for (Links link : links) {
            if (link.getRel().equals("approval_url")) {
                redirectUrl = link.getHref();
            }
        }
        return redirectUrl;
    }

    public Payment confirmPayment(HttpServletRequest request) throws  PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(request.getParameter("paymentId"));
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getParameter("PayerID"));
        APIContext apiContext = payPalProperties.apiContext();
        return payment.execute(apiContext, paymentExecution);
    }
}
