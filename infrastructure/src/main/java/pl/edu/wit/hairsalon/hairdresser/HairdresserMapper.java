package pl.edu.wit.hairsalon.hairdresser;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class HairdresserMapper {

    abstract HairdresserDocument toDocument(HairdresserDto hairdresserDto);

    abstract HairdresserDto toDto(HairdresserDocument hairdresserDocument);

}
