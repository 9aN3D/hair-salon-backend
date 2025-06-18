package pl.edu.wit.hairsalon.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.query.NotificationFindQuery;

import static java.util.Objects.requireNonNull;

class LoggableNotificationFacade implements NotificationFacade {

    private final Logger log;
    private final NotificationFacade delegate;

    LoggableNotificationFacade(NotificationFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableNotificationFacade.class);
        this.delegate = delegate;
    }

    @Override
    public void send(NotificationSendCommand command) {
        log.trace("Sending notification {command: {}}", command);
        requireNonNull(command, "Notification send command must not be null");
        delegate.send(command);
        log.info("Sent notification {type: {}, recipient: {}}", command.type(), command.recipientId());
    }

    @Override
    public Page<NotificationDto> findAll(NotificationFindQuery findQuery, Pageable pageable) {
        log.trace("Searching notifications {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Notification find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched notifications {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
