package pl.edu.wit.hairsalon.hairdresser;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;

@Component
@Mapper(componentModel = "spring")
abstract class HairdresserMapper {

    abstract HairdresserDocument toDocument(HairdresserDto hairdresserDto);

    abstract HairdresserDto toDto(HairdresserDocument hairdresserDocument);

}
