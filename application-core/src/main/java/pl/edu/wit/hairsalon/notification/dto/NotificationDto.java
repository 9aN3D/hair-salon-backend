package pl.edu.wit.hairsalon.notification.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@ToString(exclude = "errorMessage")
@EqualsAndHashCode(of = "id")
public class NotificationDto {

    String id;

    NotificationTypeDto type;

    NotificationContentDto content;

    NotificationStatusDto status;

    String errorMessage;

    String recipientId;

    String shipmentId;

    LocalDateTime creationDateTime;

    LocalDateTime modificationDateTime;

}
