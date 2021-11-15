package pl.edu.wit.hairsalon.notification;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationStatusDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@QueryEntity
@Document(value = "Notification")
@EqualsAndHashCode(of = "id")
class NotificationDocument {

    @Id
    private String id;

    private NotificationTypeDto type;

    private NotificationContentDto content;

    private NotificationStatusDto status;

    private String errorMessage;

    private String recipientId;

    private String shipmentId;

    private LocalDateTime creationDateTime;

    private LocalDateTime modificationDateTime;

}
