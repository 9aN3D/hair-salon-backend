package pl.edu.wit.hairsalon.notification;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class NotificationMapper {

    NotificationDocument toDocument(NotificationDto dto) {
        return NotificationDocument.builder()
                .id(dto.getId())
                .type(dto.getType())
                .content(dto.getContent())
                .status(dto.getStatus())
                .errorMessage(dto.getErrorMessage())
                .recipientId(dto.getRecipientId())
                .shipmentId(dto.getShipmentId())
                .creationDateTime(dto.getCreationDateTime())
                .modificationDateTime(dto.getModificationDateTime())
                .build();
    }

    NotificationDto toDto(NotificationDocument document) {
        return NotificationDto.builder()
                .id(document.getId())
                .type(document.getType())
                .content(document.getContent())
                .status(document.getStatus())
                .errorMessage(document.getErrorMessage())
                .recipientId(document.getRecipientId())
                .creationDateTime(document.getCreationDateTime())
                .modificationDateTime(document.getModificationDateTime())
                .build();
    }

}
