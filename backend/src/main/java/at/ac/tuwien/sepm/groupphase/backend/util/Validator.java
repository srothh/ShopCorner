package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.InvoiceService;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void validateNewOperator(Operator operator, OperatorService operatorService) {
        LOGGER.trace("validateNewOperator({})", operator);

        List<Operator> operators = operatorService.findAll();
        for (Operator op : operators) {

            if (op.getEmail().equals(operator.getEmail())) {
                throw new ValidationException("Account already exists");
            }

            if (op.getLoginName().equals(operator.getLoginName())) {
                throw new ValidationException("Account already exists");
            }
        }

    }

    public void validateNewInvoice(Invoice invoice) {
        LOGGER.trace("validateNewInvoice({})", invoice);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minus(1, ChronoUnit.DAYS);
        if (invoice.getAmount() <= 0) {
            throw new ValidationException("Something went wrong with the total calculation");
        }
        if (invoice.getDate() == null || invoice.getDate().isBefore(yesterday) || invoice.getDate().isAfter(today)) {
            throw new ValidationException("The invoice date is not valid");
        }
    }


    /**
     *  Is not neccessery because of the hashSet.
     */
    public void validateNewInvoiceItem(Set<InvoiceItem> items) {
        LOGGER.trace("validateNewInvoiceItem({})", items);
        if (items == null) {
            throw new ValidationException("There are no items in the invoice");
        } else {
            List<Product> productList = new ArrayList<>();
            for (InvoiceItem item : items) {

                if (item.getInvoice() == null) {
                    throw new ValidationException("Creating item entries without invoice");
                }
                if (item.getProduct() == null) {
                    throw new ValidationException("Creating item entries without products");
                }
                if (item.getNumberOfItems() <= 0) {
                    throw new ValidationException("Creating item entries without a quantity");
                }
                if (!productList.contains(item.getProduct())) {
                    productList.add(item.getProduct());
                } else {
                    throw new ValidationException("There are same items in the invoice");
                }
            }

        }
    }

}
