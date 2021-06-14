package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.util.MailTextBuilder;
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
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final MailTextBuilder mailTextBuilder;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, MailTextBuilder mailTextBuilder) {
        this.emailSender = emailSender;
        this.mailTextBuilder = mailTextBuilder;
    }


    @Override
    public void sendMail(Order order) {
        String message = mailTextBuilder.buildOrderMessage();

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, "utf-8");
        try {
            helper.setTo("michaelsteinkellner97@gmail.com");
            helper.setSubject("Ihre Bestellung");
            helper.setFrom("ShopCornerSepm@gmail.com");
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new URLDataSource(new URL("https://i.imgur.com/zMBx1FY.png"));
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<logo>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
            email.setContent(multipart);
        } catch (MessagingException | MalformedURLException e) {
            throw new RuntimeException("Fehler beim erstellen der Verifizierungs E-mail");
        }
        emailSender.send(email);
    }
}
