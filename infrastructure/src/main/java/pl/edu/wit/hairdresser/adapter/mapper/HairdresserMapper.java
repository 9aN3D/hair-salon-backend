package pl.edu.wit.hairdresser.adapter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairdresser.adapter.model.HairdresserDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class HairdresserMapper {

    public abstract HairdresserDocument toDocument(HairdresserDto hairdresserDto);

    public abstract HairdresserDto toDto(HairdresserDocument hairdresserDocument);

}
