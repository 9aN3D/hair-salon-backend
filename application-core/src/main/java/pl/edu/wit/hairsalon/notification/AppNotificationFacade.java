package pl.edu.wit.hairsalon.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.query.NotificationFindQuery;

import static java.util.Objects.requireNonNull;

class AppNotificationFacade implements NotificationFacade {

    private final NotificationPort notificationPort;
    private final NotificationSenderFactory senderFactory;

    AppNotificationFacade(NotificationPort notificationPort, NotificationSenderFactory senderFactory) {
        this.notificationPort = notificationPort;
        this.senderFactory = senderFactory;
    }

    @Override
    public void send(NotificationSendCommand command) {
        requireNonNull(command, "Notification send command must not be null");
        senderFactory.get(command.type()).send(command);
    }

    @Override
    public Page<NotificationDto> findAll(NotificationFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Notification find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return notificationPort.findAll(findQuery, pageable);
    }
}
