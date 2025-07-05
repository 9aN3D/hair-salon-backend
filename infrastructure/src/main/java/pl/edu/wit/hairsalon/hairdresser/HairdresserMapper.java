package pl.edu.wit.hairsalon.hairdresser;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.sharedKernel.document.EmbeddableTimeRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@Mapper(componentModel = "spring")
abstract class HairdresserMapper {

    @Mapping(target = "daySchedules", source = "weeklySchedule", qualifiedByName = "toEmbeddableHairdresserDaySchedules")
    abstract HairdresserDocument toDocument(HairdresserDto hairdresserDto);

    @Mapping(target = "weeklySchedule", source = "daySchedules", qualifiedByName = "toTimeRangeDtoByDayOfWeek")
    abstract HairdresserDto toDto(HairdresserDocument hairdresserDocument);

    @Named("toEmbeddableHairdresserDaySchedules")
    Set<EmbeddableHairdresserDaySchedule> toEmbeddableHairdresserDaySchedules(Map<DayOfWeek, TimeRangeDto> weeklySchedule) {
        return weeklySchedule.entrySet()
                .stream()
                .map(entry -> new EmbeddableHairdresserDaySchedule(entry.getKey(), toEmbeddableTimeRange(entry.getValue())))
                .collect(Collectors.toSet());
    }

    @Named("toTimeRangeDtoByDayOfWeek")
    Map<DayOfWeek, TimeRangeDto> toTimeRangeDtoByDayOfWeek(Set<EmbeddableHairdresserDaySchedule> daySchedules) {
        if (isNull(daySchedules)) {
            return Map.of();
        }
        return daySchedules.stream()
                .collect(Collectors.toMap(EmbeddableHairdresserDaySchedule::dayOfWeek, value -> toTimeRangeDto(value.timeRange())));
    }

    private EmbeddableTimeRange toEmbeddableTimeRange(TimeRangeDto timeRangeDto) {
        if (isNull(timeRangeDto) || timeRangeDto.isEmpty()) {
            return null;
        }
        return new EmbeddableTimeRange(
                timeRangeDto.start(),
                timeRangeDto.end()
        );
    }

    private TimeRangeDto toTimeRangeDto(EmbeddableTimeRange timeRange) {
        if (isNull(timeRange)) {
            return TimeRangeDto.empty();
        }
        return new TimeRangeDto(
                timeRange.start(),
                timeRange.end()
        );
    }

}
