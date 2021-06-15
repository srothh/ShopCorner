package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.config.properties.PayPalProperties;
import at.ac.tuwien.sepm.groupphase.backend.service.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("api/v1/paypal")
public class PayPalEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PayPalService payPalService;


    @Autowired
    public PayPalEndpoint(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Secured({"ROLE_CUSTOMER"})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Posts a new request to PayPal's API to initiate a payment", security = @SecurityRequirement(name = "apiKey"))
    public String createPayment(@RequestParam("price") Double price) throws PayPalRESTException {
        return this.payPalService.createPayment(price);
    }

    @Secured({"ROLE_CUSTOMER"})
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Confirms a payment", security = @SecurityRequirement(name = "apiKey"))
    public String confirmPayment(HttpServletRequest request) throws PayPalRESTException {
        return this.payPalService.confirmPayment(request).toJSON();
    }

}
