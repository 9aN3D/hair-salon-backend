package pl.edu.wit.hairsalon.scheduledEvent;

import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

record ScheduledEvent(
        String id,
        DateRange times,
        String hairdresserId,
        ScheduledEventType type,
        String description,
        String reservationId
) implements SelfValidator<ScheduledEvent> {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScheduledEvent that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public ScheduledEvent validate() {
        requireNonNull(times, "Scheduled event times must not be null");
        requireNonNull(type, "Scheduled event type must not be null");
        validate(new NotBlankString(id), new NotBlankString(hairdresserId), new NotBlankString(reservationId), times);
        return this;
    }

    ScheduledEventDto toDto() {
        return ScheduledEventDto.builder()
                .id(id)
                .times(times.toDto())
                .hairdresserId(hairdresserId)
                .type(type.toDto())
                .description(description)
                .reservationId(reservationId)
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private DateRange times;
        private String hairdresserId;
        private ScheduledEventType type;
        private String description;
        private String reservationId;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder times(DateRange times) {
            this.times = times;
            return this;
        }

        Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        Builder type(ScheduledEventType type) {
            this.type = type;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        ScheduledEvent build() {
            return new ScheduledEvent(id, times, hairdresserId, type, description, reservationId);
        }

    }

}
