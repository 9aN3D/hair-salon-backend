package pl.edu.wit.hairsalon.scheduledEvent.query;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record ScheduledEventFindQuery(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        DateRangeDto overlapsTimes,
        String hairdresserId,
        String reservationId
) {

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
        if (nonNull(overlapsTimes) && nonNull(overlapsTimes.start()) && nonNull(overlapsTimes.end())) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private DateRangeDto overlapsTimes;
        private String hairdresserId;
        private String reservationId;

        public Builder startDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder endDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public Builder overlapsTimes(DateRangeDto overlapsTimes) {
            this.overlapsTimes = overlapsTimes;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ScheduledEventFindQuery build() {
            return new ScheduledEventFindQuery(startDateTime, endDateTime, overlapsTimes, hairdresserId, reservationId);
        }

    }

}
