package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

public record DateRange(Range<LocalDateTime> range) implements Range<LocalDateTime> {

    public DateRange(DateRangeDto arg) {
        this(new Range.DefaultRange<>(arg.start(), arg.end()));
    }

    public DateRange(LocalDateTime start, LocalDateTime end) {
        this(new Range.DefaultRange<>(start, end));
    }

    @Override
    public boolean overlaps(Range<LocalDateTime> arg) {
        requireNonNull(arg, "Time range must not be null");
        return range.overlaps(arg);
    }

    @Override
    public boolean includes(Range<LocalDateTime> arg) {
        requireNonNull(arg, "Time range must not be null");
        return range.includes(arg);
    }

    @Override
    public boolean includes(LocalDateTime arg) {
        return range.includes(arg);
    }

    @Override
    public boolean isEmpty() {
        return range.isEmpty();
    }

    @Override
    public LocalDateTime start() {
        return range.start();
    }

    @Override
    public LocalDateTime end() {
        return range.end();
    }

    public DateRangeDto toDto() {
        return new DateRangeDto(range.start(), range.end());
    }

    public String formatStart(String pattern) {
        return format(range.start(), pattern);
    }

    public String formatEnd(String pattern) {
        return format(range.end(), pattern);
    }

    @Override
    public DateRange validate() {
        requireNonNull(range.start(), "Date range start must not be null");
        requireNonNull(range.end(), "Date range end must not be null");
        range.validate();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DateRange(Range<LocalDateTime> arg))) {
            return false;
        }
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

    @Override
    public int compareTo(Range<LocalDateTime> arg) {
        return range.compareTo(arg);
    }

    private String format(LocalDateTime dateTime, String pattern) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(dateTimeFormatter);
    }

}
