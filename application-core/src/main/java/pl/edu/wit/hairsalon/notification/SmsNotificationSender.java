package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;

import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.SMS;

class SmsNotificationSender implements NotificationSender {

    private final SmsSenderPort smsSenderPort;
    private final NotificationCreator creator;
    private final NotificationPort notificationPort;

    SmsNotificationSender(SmsSenderPort smsSenderPort, NotificationCreator creator, NotificationPort notificationPort) {
        this.smsSenderPort = smsSenderPort;
        this.creator = creator;
        this.notificationPort = notificationPort;
    }

    @Override
    public NotificationDto send(NotificationSendCommand command) {
        var newNotification = creator.create(command);
        var smsShipmentDto = smsSenderPort.send((SmsNotificationContentDto) newNotification.toDto().content());
        return notificationPort.save(newNotification
                .sentSmsResult(smsShipmentDto)
                .validate()
                .toDto());
    }

    @Override
    public NotificationTypeDto getType() {
        return SMS;
    }
}
