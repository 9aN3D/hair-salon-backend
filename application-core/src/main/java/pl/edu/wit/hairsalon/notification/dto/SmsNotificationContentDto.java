package pl.edu.wit.hairsalon.notification.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(exclude = "body")
public class SmsNotificationContentDto implements NotificationContentDto {

    String to;

    String body;

}
