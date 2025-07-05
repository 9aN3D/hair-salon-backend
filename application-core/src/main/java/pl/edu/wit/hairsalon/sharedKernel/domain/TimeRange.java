package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static java.util.Objects.requireNonNull;

public record TimeRange(Range<LocalTime> range) implements Range<LocalTime> {

    public TimeRange(TimeRangeDto timeRange) {
        this(new Range.DefaultRange<>(timeRange.start(), timeRange.end()));
    }

    public static TimeRange of(LocalTime start, LocalTime end) {
        return new TimeRange(new Range.DefaultRange<>(start, end));
    }

    public static TimeRange empty() {
        return of(null, null);
    }

    @Override
    public TimeRange validate() {
        requireNonNull(range.start(), "Time range start must not be null");
        requireNonNull(range.end(), "Time range end must not be null");
        range.validate();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeRange(Range<LocalTime> arg))) return false;
        return range.equals(arg);
    }

    @Override
    public int hashCode() {
        return range.hashCode();
    }

    @Override
    public String toString() {
        return range.toString();
    }

    public LocalTime start() {
        return range.start();
    }

    public LocalTime end() {
        return range.end();
    }

    @Override
    public boolean overlaps(Range<LocalTime> arg) {
        requireNonNull(arg, "Time range must not be null");
        return range.overlaps(arg);
    }

    @Override
    public boolean includes(Range<LocalTime> arg) {
        requireNonNull(arg, "Time range must not be null");
        return range.includes(arg);
    }

    @Override
    public boolean includes(LocalTime arg) {
        return range.includes(arg);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public int compareTo(Range<LocalTime> o) {
        return range.compareTo(o);
    }

    public TimeRangeDto toDto() {
        return new TimeRangeDto(range.start(), range.end());
    }

    public DateRangeDto toDto(LocalDate date) {
        return new DateRangeDto(date.atTime(start()), date.atTime(end()));
    }

    public TimeRange expandBy(Duration duration) {
        return TimeRange.of(
                start().minus(duration),
                end().plus(duration)
        );
    }

    public Duration duration() {
        return Duration.between(start(), end());
    }

}
