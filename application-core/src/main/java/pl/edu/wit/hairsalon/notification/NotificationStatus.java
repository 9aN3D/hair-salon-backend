package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.NotificationStatusDto;

enum NotificationStatus {

    SENT,
    DELIVERED,
    UNDELIVERED,
    ERROR;

    NotificationStatusDto toDto() {
        return NotificationStatusDto.valueOf(this.name());
    }

}
