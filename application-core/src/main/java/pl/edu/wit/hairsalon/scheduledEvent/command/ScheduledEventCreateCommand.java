package pl.edu.wit.hairsalon.scheduledEvent.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledEventCreateCommand {

    private DateRangeDto times;

    private ScheduledEventTypeDto type;

    private String hairdresserId;

    private String reservationId;

}
