package pl.edu.wit.hairsalon.hairdresser;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableTimeRange;

import static pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto.*;

@Component
@Mapper(componentModel = "spring")
abstract class HairdresserDayOverrideMapper {

    HairdresserDayOverrideDocument toDocument(HairdresserDayOverrideDto dto) {
        return HairdresserDayOverrideDocument.builder()
                .id(new EmbeddableHairdresserDayOverrideId(dto.id()))
                .timeRange(toTimeRange(dto))
                .build();
    }

    HairdresserDayOverrideDto toDto(HairdresserDayOverrideDocument document) {
        return document.hasTimeRange()
                ? new CustomHoursOverrideDto(document.getId().toDto(), document.getTimeRange().toDto())
                : new ClosedOverrideDto(document.getId().toDto());
    }

    private EmbeddableTimeRange toTimeRange(HairdresserDayOverrideDto dto) {
        return switch (dto) {
            case ClosedOverrideDto closedOverride -> null;
            case CustomHoursOverrideDto customHoursOverride -> new EmbeddableTimeRange(
                    customHoursOverride.timeRange().start(),
                    customHoursOverride.timeRange().end()
            );
        };
    }

}
