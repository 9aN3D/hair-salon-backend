package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.SENT;

class NotificationCreator {

    private final IdGenerator idGenerator;

    NotificationCreator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    Notification create(NotificationSendCommand command) {
        return createNewNotification(command)
                .validate();
    }

    private Notification createNewNotification(NotificationSendCommand command) {
        var notificationType = NotificationType.valueOf(command.type());
        var now = now();
        return Notification.builder()
                .id(idGenerator.generate())
                .type(notificationType)
                .status(SENT)
                .content(notificationType.createInstance(command.content()))
                .recipientId(command.recipientId())
                .creationDateTime(now)
                .modificationDateTime(now)
                .build();
    }

}
