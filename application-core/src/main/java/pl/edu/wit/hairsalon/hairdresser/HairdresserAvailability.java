package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

class HairdresserAvailability {

    private final Hairdresser hairdresser;
    private final Function<HairdresserDayOverrideId, Optional<HairdresserDayOverride>> dayOverrideFunction;

    HairdresserAvailability(Hairdresser hairdresser, Function<HairdresserDayOverrideId, Optional<HairdresserDayOverride>> dayOverrideFunction) {
        this.hairdresser = hairdresser;
        this.dayOverrideFunction = dayOverrideFunction;
    }

    Optional<TimeRange> workingHoursFor(LocalDate date) {
        return dayOverrideFunction.apply(new HairdresserDayOverrideId(hairdresser.id(), date))
                .map(this::toTimeRange)
                .orElseGet(() -> hairdresser.weeklySchedule().getTimeRangeForDay(date.getDayOfWeek()));

    }

    private Optional<TimeRange> toTimeRange(HairdresserDayOverride hairdresserDayOverride) {
        return switch (hairdresserDayOverride) {
            case ClosedOverride __ -> Optional.empty();
            case CustomHoursOverride custom -> Optional.of(new TimeRange(custom.value().range()));
        };
    }
    
}
