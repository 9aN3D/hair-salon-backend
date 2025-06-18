package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationStatusDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsShipmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.ERROR;
import static pl.edu.wit.hairsalon.notification.NotificationStatus.SENT;
import static pl.edu.wit.hairsalon.notification.NotificationType.SMS;

record Notification(
        String id,
        NotificationType type,
        NotificationStatus status,
        NotificationContent content,
        String errorMessage,
        String recipientId,
        String shipmentId,
        LocalDateTime creationDateTime,
        LocalDateTime modificationDateTime
) implements SelfValidator<Notification> {

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Notification that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
        var shipmentStatus = smsShipmentDto.status();
        return Notification.builder()
                .id(id)
                .type(type)
                .status(NotificationStatus.valueOf(shipmentStatus.getNotificationStatus().name()))
                .content(content)
                .errorMessage(prepareErrorMessage(shipmentStatus))
                .recipientId(recipientId)
                .shipmentId(smsShipmentDto.id())
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private NotificationType type;
        private NotificationStatus status;
        private NotificationContent content;
        private String errorMessage;
        private String recipientId;
        private String shipmentId;
        private LocalDateTime creationDateTime;
        private LocalDateTime modificationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder status(NotificationStatus status) {
            this.status = status;
            return this;
        }

        public Builder content(NotificationContent content) {
            this.content = content;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder recipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public Builder shipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public Builder modificationDateTime(LocalDateTime modificationDateTime) {
            this.modificationDateTime = modificationDateTime;
            return this;
        }

        public Notification build() {
            return new Notification(
                    id,
                    type,
                    status,
                    content,
                    errorMessage,
                    recipientId,
                    shipmentId,
                    creationDateTime,
                    modificationDateTime
            );
        }

    }

}
