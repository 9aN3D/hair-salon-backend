package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;

public interface EmailSenderPort {

    boolean send(EmailNotificationContentDto notificationContent);

}
