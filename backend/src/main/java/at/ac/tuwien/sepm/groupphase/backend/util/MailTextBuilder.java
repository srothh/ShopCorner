package at.ac.tuwien.sepm.groupphase.backend.util;

import org.springframework.stereotype.Component;

@Component
public class MailTextBuilder {

    public MailTextBuilder() {
    }

    public String buildMessage() {
        StringBuilder message = new StringBuilder();

        message.append("<h1>This is a test</h1>");
        message.append("Now this is just text");

        return message.toString();
    }
}
