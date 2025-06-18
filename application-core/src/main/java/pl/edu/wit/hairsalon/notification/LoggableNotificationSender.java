package pl.edu.wit.hairsalon.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

class LoggableNotificationSender implements NotificationSender {

    private final Logger log;
    private final NotificationSender delegate;

    LoggableNotificationSender(NotificationSender delegate) {
        this.log = LoggerFactory.getLogger(LoggableNotificationSender.class);
        this.delegate = delegate;
    }

    @Override
    public NotificationDto send(NotificationSendCommand command) {
        log.trace("Sending {} notification {command: {}}", getType(), command);
        var result = delegate.send(command);
        log.info("Sent {} notification {result: {}}", getType(), result);
        return result;
    }

    @Override
    public NotificationTypeDto getType() {
        return delegate.getType();
    }

}
