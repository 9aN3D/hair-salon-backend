package pl.edu.wit.hairsalon.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;

import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.SMS;

@Slf4j
@RequiredArgsConstructor
class SmsNotificationSender implements NotificationSender {

    private final SmsSenderPort smsSenderPort;
    private final NotificationCreator creator;
    private final NotificationPort notificationPort;

    @Override
    public NotificationDto send(NotificationSendCommand command) {
        log.trace("Sending sms notification: {command: {}}", command);
        var newNotification = creator.create(command);
        var smsShipmentDto = smsSenderPort.send((SmsNotificationContentDto) newNotification.toDto().getContent());
        var savedNotification = notificationPort.save(newNotification.sentSmsResult(smsShipmentDto)
                .validate()
                .toDto());
        log.info("Sent sms notification: {result: {}}", savedNotification);
        return savedNotification;
    }

    @Override
    public NotificationTypeDto getType() {
        return SMS;
    }

}
