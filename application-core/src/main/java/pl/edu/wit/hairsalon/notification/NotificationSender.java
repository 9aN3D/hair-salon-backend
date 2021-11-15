package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

interface NotificationSender {

    NotificationDto send(NotificationSendCommand command);

    NotificationTypeDto getType();

}
