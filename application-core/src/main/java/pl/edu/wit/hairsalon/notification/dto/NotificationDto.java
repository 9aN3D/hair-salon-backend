package pl.edu.wit.hairsalon.notification.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        String id,
        NotificationTypeDto type,
        NotificationContentDto content,
        NotificationStatusDto status,
        String errorMessage,
        String recipientId,
        String shipmentId,
        LocalDateTime creationDateTime,
        LocalDateTime modificationDateTime
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private NotificationTypeDto type;
        private NotificationContentDto content;
        private NotificationStatusDto status;
        private String errorMessage;
        private String recipientId;
        private String shipmentId;
        private LocalDateTime creationDateTime;
        private LocalDateTime modificationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(NotificationTypeDto type) {
            this.type = type;
            return this;
        }

        public Builder content(NotificationContentDto content) {
            this.content = content;
            return this;
        }

        public Builder status(NotificationStatusDto status) {
            this.status = status;
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

        public NotificationDto build() {
            return new NotificationDto(
                    id,
                    type,
                    content,
                    status,
                    errorMessage,
                    recipientId,
                    shipmentId,
                    creationDateTime,
                    modificationDateTime
            );
        }

    }

}
