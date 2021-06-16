package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine thymeleafTemplateEngine;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, TemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void sendMail() {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("name", "Karl");
        thymeleafContext.setVariable("address", "Wien");
        thymeleafContext.setVariable("sum", 10);
        thymeleafContext.setVariable("tax", 20);
        thymeleafContext.setVariable("end", 12);
        String htmlBody = thymeleafTemplateEngine.process("emailTemplate.html", thymeleafContext);

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, "utf-8");
        try {
            helper.setTo("michaelsteinkellner97@gmail.com");
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
        } catch (MessagingException | MalformedURLException e) {
            throw new RuntimeException("Fehler beim erstellen der Verifizierungs E-mail", e);
        }
        emailSender.send(email);
    }
}
