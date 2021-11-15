package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;

class NotificationSenderFactory {

    private final Map<NotificationTypeDto, NotificationSender> notificationSenders;

    NotificationSenderFactory(Set<NotificationSender> arg) {
        this.notificationSenders = arg.stream()
                .collect(Collectors.toMap(NotificationSender::getType, identity()));
    }

    NotificationSender get(NotificationTypeDto type) {
        return ofNullable(notificationSenders.get(type))
                .orElseThrow(() -> new IllegalArgumentException(
                        format("Type %s not implemented", type)
                ));
    }

}
