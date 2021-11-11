package pl.edu.wit.hairsalon.scheduledevent.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledEventFindQuery {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private DateRangeDto overlapsTimes;

    private String hairdresserId;

    private String reservationId;

    public static ScheduledEventFindQuery of(DateRangeDto dateRange, String hairdresserId) {
        return ScheduledEventFindQuery.builder()
                .overlapsTimes(dateRange)
                .hairdresserId(hairdresserId)
                .build();
    }

    public void ifIncludesStartAndEndDateTimesPresent(Consumer<DateRangeDto> action) {
        if (nonNull(startDateTime) && nonNull(endDateTime)) {
            action.accept(new DateRangeDto(startDateTime, endDateTime));
        }
    }

    public void ifOverlapsTimesPresent(Consumer<DateRangeDto> action) {
        if (nonNull(overlapsTimes) && nonNull(overlapsTimes.getStart()) && nonNull(overlapsTimes.getEnd())) {
            action.accept(overlapsTimes);
        }
    }

    public void ifHairdresserIdPresent(Consumer<String> action) {
        if (nonNull(hairdresserId) && !hairdresserId.trim().isBlank()) {
            action.accept(hairdresserId);
        }
    }

    public void ifReservationIdPresent(Consumer<String> action) {
        if (nonNull(reservationId) && !reservationId.trim().isBlank()) {
            action.accept(reservationId);
        }
    }

}
