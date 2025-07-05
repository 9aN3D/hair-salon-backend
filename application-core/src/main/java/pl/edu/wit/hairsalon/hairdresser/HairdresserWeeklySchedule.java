package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

record HairdresserWeeklySchedule(Set<HairdresserDaySchedule> value) implements SelfValidator<HairdresserWeeklySchedule> {

    public HairdresserWeeklySchedule(Map<DayOfWeek, TimeRangeDto> dayOfWeekToTimeRange) {
        this(
                dayOfWeekToTimeRange.entrySet()
                        .stream()
                        .map(entry -> new HairdresserDaySchedule(entry.getKey(), isNull(entry.getValue()) ? TimeRange.empty() : new TimeRange(entry.getValue())))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public HairdresserWeeklySchedule validate() {
        requireNonNull(value, "Hairdresser day schedule value must not be null");
        validate(value);
        if (value.size() != 7) {
            throw new ValidationException("Hairdresser day schedule value must have exactly 7 days");
        }
        return this;
    }

    public Optional<TimeRange> getTimeRangeForDay(DayOfWeek dayOfWeek) {
        return value.stream()
                .filter(v -> v.dayOfWeek() == dayOfWeek)
                .findFirst()
                .filter(not(HairdresserDaySchedule::isDayOff))
                .map(HairdresserDaySchedule::timeRange);
    }

    public Map<DayOfWeek, TimeRangeDto> toDtoMap() {
        return value.stream()
                .collect(Collectors.toMap(HairdresserDaySchedule::dayOfWeek, value -> toTimeRangeDto(value.timeRange())));
    }

    private TimeRangeDto toTimeRangeDto(TimeRange timeRange) {
        return isNull(timeRange)
                ? TimeRangeDto.empty()
                : timeRange.toDto();
    }

}
