package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class MailTextBuilder {

    private final BufferedReader order;

    public MailTextBuilder() {
        try {
            order = new BufferedReader(new FileReader("htmlTemplates/emailTemplate.html"));
        } catch (IOException e) {
            throw new ValidationException("mail not work" + e);
        }
    }

    public String buildOrderMessage() {
        StringBuilder message = new StringBuilder();

        try {
            //BufferedReader in = new BufferedReader(new FileReader("htmlTemplates/emailTemplate.html"));
            String str;
            while ((str = order.readLine()) != null) {
                message.append(str);
            }
            order.close();
            /* String html = new String(Files.readAllBytes(Paths.get("htmlToPdfTemplate/emailTemplate.html")));
            message.append(html);*/
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        message.append("Bei Fragen kontaktieren sie uns unter... <img src=\"cid:logo\">");
        return message.toString();
    }
}
