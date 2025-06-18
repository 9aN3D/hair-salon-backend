package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.EMAIL;

class EmailNotificationSender implements NotificationSender {

    private final EmailSenderPort emailSenderPort;
    private final NotificationCreator creator;
    private final NotificationPort notificationPort;

    EmailNotificationSender(EmailSenderPort emailSenderPort, NotificationCreator creator, NotificationPort notificationPort) {
        this.emailSenderPort = emailSenderPort;
        this.creator = creator;
        this.notificationPort = notificationPort;
    }

    @Override
    public NotificationDto send(NotificationSendCommand command) {
        var newNotification = creator.create(command);
        var sent = emailSenderPort.send((EmailNotificationContentDto) newNotification.toDto().content());
        return notificationPort.save(newNotification.sentEmailResult(sent)
                .validate()
                .toDto());
    }

    @Override
    public NotificationTypeDto getType() {
        return EMAIL;
    }

}
