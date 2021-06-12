package at.ac.tuwien.sepm.groupphase.backend.util;

import org.springframework.stereotype.Component;

@Component
public class MailTextBuilder {

    public MailTextBuilder() {
    }

    public String buildMessage() {
        StringBuilder message = new StringBuilder();

        message.append("<h1>Vielen Dank für ihre Bestellung!</h1>");
        message.append("Ihre Bestellung ist bei uns eingegangen und wird so schnell wie möglich verarbeitet. <br/>");
        message.append("<h3>Übersicht über ihre Bestellung</h3>");
        message.append("<table><thead><tr><th>Produkt</th><th>Betrag</th></<tr></thead><tbody><tr><td>Stuff</td><td>1,99</td></tr></tbody></table>");
        message.append("Bei Fragen kontaktieren sie uns unter... <img src=\"cid:logo\">");
        return message.toString();
    }
}
