package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;

interface NotificationContent extends SelfValidator<NotificationContent> {

    String to();

    String body();

    NotificationContentDto toDto();

}
