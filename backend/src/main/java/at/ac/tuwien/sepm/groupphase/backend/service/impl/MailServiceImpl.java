package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JavaMailSender emailSender;
    private final TemplateEngine thymeleafTemplateEngine;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, TemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void sendMail(Order order) {
        LOGGER.trace("sendMail({})", order);
        Context thymeleafContext = new Context();
        double tax = 0;
        double subtotal = 0;
        for (InvoiceItem i : order.getInvoice().getItems()) {
            Product p = i.getProduct();
            TaxRate t = p.getTaxRate();
            double subtotalPerProduct = p.getPrice() * i.getNumberOfItems();
            double taxPerProduct = p.getPrice() * i.getNumberOfItems() * ((t.getPercentage() / 100));
            subtotal = subtotal + subtotalPerProduct;
            tax = tax + taxPerProduct;
        }
        Address address = order.getCustomer().getAddress();
        String addressString = address.getStreet() + ' ' + address.getHouseNumber() + '/' + address.getDoorNumber() + ", " + address.getPostalCode();
        thymeleafContext.setVariable("name", replaceSpecialChar(order.getCustomer().getName()));
        thymeleafContext.setVariable("address", replaceSpecialChar(addressString));
        LOGGER.info(address.getStreet());
        thymeleafContext.setVariable("sum", subtotal);
        thymeleafContext.setVariable("tax", tax);
        thymeleafContext.setVariable("end", order.getInvoice().getAmount());
        String htmlBody = thymeleafTemplateEngine.process("emailTemplate.html", thymeleafContext);

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, "utf-8");
        try {
            helper.setTo(order.getCustomer().getEmail());
            helper.setSubject("Ihre Bestellung");
            helper.setFrom("ShopCornerSepm@gmail.com");
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new URLDataSource(new URL("https://i.imgur.com/zMBx1FY.png"));
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<logo>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
            email.setContent(multipart);
            emailSender.send(email);
        } catch (MessagingException | MalformedURLException e) {
            throw new ServiceException("Fehler beim erstellen der Verifizierungs E-mail", e);
        }
    }

    private String replaceSpecialChar(String string) {
        return string.replace("ö","&ouml").replace("ä","&auml").replace("ü","&uuml")
            .replace("Ö","&Ouml").replace("Ä","&Auml").replace("Ü","&Uuml")
            .replace("ß","&szlig;");
    }
}
