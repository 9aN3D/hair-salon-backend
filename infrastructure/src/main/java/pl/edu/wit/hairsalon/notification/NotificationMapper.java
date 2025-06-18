package pl.edu.wit.hairsalon.notification;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
abstract class NotificationMapper {

    NotificationDocument toDocument(NotificationDto dto) {
        return NotificationDocument.builder()
                .id(dto.id())
                .type(dto.type())
                .content(dto.content())
                .status(dto.status())
                .errorMessage(dto.errorMessage())
                .recipientId(dto.recipientId())
                .shipmentId(dto.shipmentId())
                .creationDateTime(dto.creationDateTime())
                .modificationDateTime(dto.modificationDateTime())
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
