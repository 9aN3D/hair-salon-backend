package pl.edu.wit.hairsalon.notification;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.notification.dto.NotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationStatusDto;
import pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto;

import java.time.LocalDateTime;
import java.util.Objects;

@QueryEntity
@Document(value = "Notification")
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

    NotificationDocument() {
    }

    NotificationDocument(String id, NotificationTypeDto type, NotificationContentDto content, NotificationStatusDto status,
                         String errorMessage, String recipientId, String shipmentId,
                         LocalDateTime creationDateTime, LocalDateTime modificationDateTime) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.status = status;
        this.errorMessage = errorMessage;
        this.recipientId = recipientId;
        this.shipmentId = shipmentId;
        this.creationDateTime = creationDateTime;
        this.modificationDateTime = modificationDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationTypeDto getType() {
        return type;
    }

    public void setType(NotificationTypeDto type) {
        this.type = type;
    }

    public NotificationContentDto getContent() {
        return content;
    }

    public void setContent(NotificationContentDto content) {
        this.content = content;
    }

    public NotificationStatusDto getStatus() {
        return status;
    }

    public void setStatus(NotificationStatusDto status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationDocument)) return false;
        NotificationDocument that = (NotificationDocument) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

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

        public NotificationDocument build() {
            return new NotificationDocument(id, type, content, status, errorMessage, recipientId, shipmentId, creationDateTime, modificationDateTime);
        }

    }

}
