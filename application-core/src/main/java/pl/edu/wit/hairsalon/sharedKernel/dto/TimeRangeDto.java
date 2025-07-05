package pl.edu.wit.hairsalon.sharedKernel.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.util.Objects.isNull;

public record TimeRangeDto(LocalTime start, LocalTime end) {

    public static TimeRangeDto empty() {
        return new TimeRangeDto(null, null);
    }

    public boolean isEmpty() {
        return isNull(start) && isNull(end);
    }

    public DateRangeDto toDateRangeDto(LocalDate date) {
        return new DateRangeDto(
                date.atTime(start),
                date.atTime(end)
        );
    }

}
