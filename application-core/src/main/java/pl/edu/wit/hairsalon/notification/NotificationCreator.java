package pl.edu.wit.hairsalon.notification;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import static java.time.LocalDateTime.now;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.SENT;

@RequiredArgsConstructor
class NotificationCreator {

    private final IdGenerator idGenerator;

    Notification create(NotificationSendCommand command) {
        return createNewNotification(command)
                .validate();
    }

    private Notification createNewNotification(NotificationSendCommand command) {
        var notificationType = NotificationType.valueOf(command.getType());
        var now = now();
        return Notification.builder()
                .id(idGenerator.generate())
                .type(notificationType)
                .status(SENT)
                .content(notificationType.createInstance(command.getContent()))
                .recipientId(command.getRecipientId())
                .creationDateTime(now)
                .modificationDateTime(now)
                .build();
    }

}
