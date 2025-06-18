package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;

enum NotificationType {

    SMS {
        @Override
        public NotificationContent createInstance(NotificationContentDto content) {
            return new SmsNotificationContent((SmsNotificationContentDto) content);
        }
    },
    EMAIL {
        @Override
        public NotificationContent createInstance(NotificationContentDto content) {
            return new EmailNotificationContent((EmailNotificationContentDto) content);
        }
    };

    public abstract NotificationContent createInstance(NotificationContentDto content);

    static NotificationType valueOf(NotificationTypeDto notificationType) {
        return NotificationType.valueOf(notificationType.name());
    }

    NotificationTypeDto toDto() {
        return NotificationTypeDto.valueOf(this.name());
    }

}
