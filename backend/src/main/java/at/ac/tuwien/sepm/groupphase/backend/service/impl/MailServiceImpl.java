package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.util.MailTextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
    public void sendMail() {
        String message = mailTextBuilder.buildMessage();

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, "utf-8");
        try {
            helper.setTo("mike1997@icloud.com");
            helper.setSubject("First test mail");
            helper.setFrom("michaelsteinkellner97@gmail.com");
            email.setContent(message, "text/html");
        } catch (MessagingException e) {
            throw new RuntimeException("Fehler beim erstellen der Verifizierungs E-mail");
        }
        emailSender.send(email);
    }
}
