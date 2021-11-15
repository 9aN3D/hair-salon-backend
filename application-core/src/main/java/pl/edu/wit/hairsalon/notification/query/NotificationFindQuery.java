package pl.edu.wit.hairsalon.notification.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationFindQuery {

    private String recipientId;

    public void ifRecipientIdPresent(Consumer<String> action) {
        if (nonNull(recipientId) && !recipientId.trim().isBlank()) {
            action.accept(recipientId);
        }
    }

}
