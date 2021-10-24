package pl.edu.wit.spring.adapter.persistence.hairdresser.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.HairdresserDto;
import pl.edu.wit.spring.adapter.persistence.hairdresser.model.HairdresserDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class HairdresserMapper {

    public abstract HairdresserDocument toDocument(HairdresserDto hairdresserDto);

    public abstract HairdresserDto toDto(HairdresserDocument hairdresserDocument);

}
