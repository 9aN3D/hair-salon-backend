package pl.edu.wit.hairsalon.notification.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSendCommand {

    private String recipientId;

    private NotificationTypeDto type;

    private NotificationContentDto content;

}
