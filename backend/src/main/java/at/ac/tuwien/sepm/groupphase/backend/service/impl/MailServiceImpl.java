package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfGeneratorService;
import at.ac.tuwien.sepm.groupphase.backend.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JavaMailSender emailSender;
    private final TemplateEngine thymeleafTemplateEngine;

    private final PdfGeneratorService pdfGeneratorService;
    private final ShopService shopService;


    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, TemplateEngine thymeleafTemplateEngine, @Lazy PdfGeneratorService pdfGeneratorService,
                           ShopService shopService) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.pdfGeneratorService = pdfGeneratorService;
        this.shopService = shopService;
    }

    @Override
    public void sendMail(Order order, CancellationPeriod cancellationPeriod) throws IOException {
        LOGGER.trace("sendMail({})", order);
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
        String addressString = address.getStreet() + ' ' + address.getHouseNumber();
        if (!address.getDoorNumber().isBlank()) {
            addressString += '/' + address.getDoorNumber();
        }
        addressString += ", " + address.getPostalCode();
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("shop", replaceSpecialChar(shopService.getSettings().getTitle()));
        thymeleafContext.setVariable("name", replaceSpecialChar(order.getCustomer().getName()));
        thymeleafContext.setVariable("address", replaceSpecialChar(addressString));
        thymeleafContext.setVariable("sum", (double) Math.round(subtotal * 100) / 100);
        thymeleafContext.setVariable("tax", (double) Math.round(tax * 100) / 100);
        thymeleafContext.setVariable("end", (double) Math.round(order.getInvoice().getAmount() * 100) / 100);
        if (cancellationPeriod.getDays() == 0) {
            thymeleafContext.setVariable("cancel", "Diese Bestellung ist unwiderruflich!");
        } else {
            thymeleafContext.setVariable("cancel", "Sie k&ouml;nnen ihr Bestellung innerhalb eines Zeitraums von " + cancellationPeriod.getDays() + " Tagen unter\n"
                + "    <a href=\"http://localhost:4200/#/account/orders\">Meine Bestellungen</a> stornieren.");
        }
        Promotion promotion = order.getPromotion();
        if (promotion != null) {
            thymeleafContext.setVariable("promotionDiscount", (double) -Math.round(promotion.getDiscount() * 100) / 100);
        }
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
            ByteArrayDataSource ds = new ByteArrayDataSource(pdfGeneratorService.createPdfInvoiceCustomer(order), "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(ds));
            messageBodyPart.setFileName("Rechnung.pdf");
            multipart.addBodyPart(messageBodyPart);
            email.setContent(multipart);
            emailSender.send(email);
        } catch (MessagingException e) {
            throw new ServiceException("Fehler beim erstellen der Verifizierungs E-mail", e);
        }
    }

    private String replaceSpecialChar(String string) {
        return string.replace("ö", "&ouml").replace("ä", "&auml").replace("ü", "&uuml")
            .replace("Ö", "&Ouml").replace("Ä", "&Auml").replace("Ü", "&Uuml")
            .replace("ß", "&szlig;");
    }
}
