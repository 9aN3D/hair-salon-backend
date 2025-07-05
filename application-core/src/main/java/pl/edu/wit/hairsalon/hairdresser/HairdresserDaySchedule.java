package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.TimeRange;

import java.time.DayOfWeek;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

record HairdresserDaySchedule(
        DayOfWeek dayOfWeek,
        TimeRange timeRange
) implements SelfValidator<HairdresserDaySchedule> {

    public boolean isDayOff() {
        return isNull(timeRange) || timeRange.isEmpty();
    }

    @Override
    public HairdresserDaySchedule validate() {
        requireNonNull(dayOfWeek, "HairdresserDaySchedule dayOfWeek must not be null");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HairdresserDaySchedule that)) return false;
        return dayOfWeek == that.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dayOfWeek);
    }

}
