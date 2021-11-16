package pl.edu.wit.hairsalon.scheduledEvent;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;

@Component
@Mapper(componentModel = "spring")
abstract class ScheduledEventMapper {

    abstract ScheduledEventDto toDto(ScheduledEventDocument scheduledEventDocument);

    abstract ScheduledEventDocument toDocument(ScheduledEventDto scheduledEventDto);

}
