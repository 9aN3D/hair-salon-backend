package pl.edu.wit.hairsalon.sharedKernel.dto;

import java.time.LocalDateTime;

public record DateRangeDto(LocalDateTime start, LocalDateTime end) {

    public TimeRangeDto toTimeRangeDto() {
        return new TimeRangeDto(
                start.toLocalTime(),
                end.toLocalTime()
        );
    }

}
