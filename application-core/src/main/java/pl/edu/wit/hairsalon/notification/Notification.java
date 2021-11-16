package pl.edu.wit.hairsalon.notification;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationStatusDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.ERROR;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.SENT;
import static pl.edu.wit.hairsalon.notification.NotificationType.SMS;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Notification implements SelfValidator<Notification> {

    private final String id;
    private final NotificationType type;
    private final NotificationStatus status;
    private final NotificationContent content;
    private final String errorMessage;
    private final String recipientId;
    private final String shipmentId;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime modificationDateTime;

    @Override
    public Notification validate() {
        requireNonNull(type, "Notification type must not be null");
        requireNonNull(status, "Notification status must not be null");
        requireNonNull(content, "Notification content must not be null");
        requireNonNull(creationDateTime, "Notification creation date time must not be null");
        requireNonNull(modificationDateTime, "Notification modification date time must not be null");
        validate(new NotBlankString(id), new NotBlankString(recipientId), content);
        return this;
    }

    NotificationDto toDto() {
        return NotificationDto.builder()
                .id(id)
                .type(type.toDto())
                .status(status.toDto())
                .content(content.toDto())
                .errorMessage(errorMessage)
                .recipientId(recipientId)
                .shipmentId(shipmentId)
                .creationDateTime(creationDateTime)
                .modificationDateTime(modificationDateTime)
                .build();
    }

    Notification sentSmsResult(SmsShipmentDto smsShipmentDto) {
        var shipmentStatus = smsShipmentDto.getStatus();
        return Notification.builder()
                .id(id)
                .type(type)
                .status(NotificationStatus.valueOf(shipmentStatus.getNotificationStatus().name()))
                .content(content)
                .errorMessage(prepareErrorMessage(shipmentStatus))
                .recipientId(recipientId)
                .shipmentId(smsShipmentDto.getId())
                .creationDateTime(creationDateTime)
                .modificationDateTime(now())
                .build();
    }

    Notification sentEmailResult(boolean sent) {
        return Notification.builder()
                .id(id)
                .type(type)
                .status(sent ? SENT : ERROR)
                .content(content)
                .recipientId(recipientId)
                .creationDateTime(creationDateTime)
                .modificationDateTime(now())
                .build();
    }

    boolean hasSmsType() {
        return type == SMS;
    }

    private String prepareErrorMessage(SmsShipmentStatusDto status) {
        if (status.getNotificationStatus() == NotificationStatusDto.ERROR) {
            return status.getDescription();
        }
        return null;
    }

}
