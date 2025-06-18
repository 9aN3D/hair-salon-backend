package pl.edu.wit.hairsalon.notification.command;

import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

public record NotificationSendCommand(
        String recipientId,
        NotificationTypeDto type,
        NotificationContentDto content
) {

}
