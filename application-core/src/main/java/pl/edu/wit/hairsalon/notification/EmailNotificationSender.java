package pl.edu.wit.hairsalon.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.EMAIL;

@Slf4j
@RequiredArgsConstructor
class EmailNotificationSender implements NotificationSender {

    private final EmailSenderPort emailSenderPort;
    private final NotificationCreator creator;
    private final NotificationPort notificationPort;

    @Override
    public NotificationDto send(NotificationSendCommand command) {
        log.trace("Sending email notification: {command: {}}", command);
        var newNotification = creator.create(command);
        var sent = emailSenderPort.send((EmailNotificationContentDto) newNotification.toDto().getContent());
        var savedNotification = notificationPort.save(newNotification.sentEmailResult(sent)
                .validate()
                .toDto());
        log.info("Sent sms notification: {result: {}}", savedNotification);
        return savedNotification;
    }

    @Override
    public NotificationTypeDto getType() {
        return EMAIL;
    }

}
