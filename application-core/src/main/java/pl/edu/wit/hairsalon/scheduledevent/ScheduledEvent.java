package pl.edu.wit.hairsalon.scheduledevent;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class ScheduledEvent implements SelfValidator<ScheduledEvent> {

    private final String id;
    private final DateRange times;
    private final String hairdresserId;
    private final ScheduledEventType type;
    private final String description;
    private final String reservationId;

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

}
