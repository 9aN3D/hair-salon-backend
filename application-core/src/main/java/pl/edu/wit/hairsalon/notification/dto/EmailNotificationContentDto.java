package pl.edu.wit.hairsalon.notification.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.File;

@Value
@Builder
@EqualsAndHashCode(exclude = {"body", "attachments"})
public class EmailNotificationContentDto implements NotificationContentDto {

    String to;

    String subject;

    String body;

    boolean html;

    File[] attachments;

}
