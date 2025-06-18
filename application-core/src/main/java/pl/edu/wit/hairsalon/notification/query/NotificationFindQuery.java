package pl.edu.wit.hairsalon.notification.query;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record NotificationFindQuery(String recipientId) {

    public void ifRecipientIdPresent(Consumer<String> action) {
        if (nonNull(recipientId) && !recipientId.trim().isBlank()) {
            action.accept(recipientId);
        }
    }

}
