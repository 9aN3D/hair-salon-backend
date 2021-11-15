package pl.edu.wit.hairsalon.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.query.NotificationFindQuery;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppNotificationFacade implements NotificationFacade {

    private final NotificationPort notificationPort;
    private final NotificationSenderFactory senderFactory;

    @Override
    public void send(NotificationSendCommand command) {
        log.trace("Sending notification {command: {}}", command);
        requireNonNull(command, "Notification send command must not be null");
        var notification = senderFactory.get(command.getType()).send(command);
        log.info("Sent notification {result: {}}", notification);
    }

    @Override
    public Page<NotificationDto> findAll(NotificationFindQuery findQuery, Pageable pageable) {
        log.trace("Searching notifications {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Notification find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = notificationPort.findAll(findQuery, pageable);
        log.info("Searched notifications {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
