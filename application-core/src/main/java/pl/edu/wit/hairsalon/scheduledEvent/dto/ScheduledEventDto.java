package pl.edu.wit.hairsalon.scheduledEvent.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class ScheduledEventDto {

    String id;

    DateRangeDto times;

    String hairdresserId;

    ScheduledEventTypeDto type;

    String description;

    String reservationId;

}
