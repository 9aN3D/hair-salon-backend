package pl.edu.wit.hairsalon.scheduledevent;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventDto;

@Component
@Mapper(componentModel = "spring")
abstract class ScheduledEventMapper {

    abstract ScheduledEventDto toDto(ScheduledEventDocument scheduledEventDocument);

    abstract ScheduledEventDocument toDocument(ScheduledEventDto scheduledEventDto);

}
