package pl.edu.wit.hairsalon.sharedKernel.domain;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
public class DateRange implements Comparable<DateRange>, SelfValidator<DateRange> {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public DateRange(DateRangeDto arg) {
        start = arg.getStart();
        end = arg.getEnd();
    }

    public boolean overlaps(DateRange arg) {
        return arg.includes(start) || arg.includes(end) || this.includes(arg);
    }

    public boolean includes(DateRange arg) {
        return this.includes(arg.start) && this.includes(arg.end);
    }

    public boolean includes(LocalDateTime arg) {
        return !arg.isBefore(start) && !arg.isAfter(end);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        if (isNull(start) || isNull(end)) {
            return true;
        }
        return start.isAfter(end);
    }

    public LocalDateTime start() {
        return start;
    }

    public LocalDateTime end() {
        return end;
    }

    public DateRangeDto toDto() {
        return new DateRangeDto(start, end);
    }

    public String formatStart(String pattern) {
        return format(start, pattern);
    }

    public String formatEnd(String pattern) {
        return format(end, pattern);
    }

    @Override
    public DateRange validate() {
        requireNonNull(start, "Date range start must not be null");
        requireNonNull(end, "Date range end must not be null");
        if (isEmpty()) {
            throw new ValidationException("Start is after end");
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DateRange)) return false;
        var other = (DateRange) o;
        return start.equals(other.start) && end.equals(other.end);
    }

    @Override
    public int hashCode() {
        return start.hashCode();
    }

    @Override
    public String toString() {
        return isEmpty()
                ? "Empty date range"
                : start.toString() + " - " + end.toString();
    }

    @Override
    public int compareTo(DateRange arg) {
        return start.equals(arg.start)
                ? end.compareTo(arg.end)
                : start.compareTo(arg.start);
    }

    private String format(LocalDateTime dateTime, String pattern) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(dateTimeFormatter);
    }

}
