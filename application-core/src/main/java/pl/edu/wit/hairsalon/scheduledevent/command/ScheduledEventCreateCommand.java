package pl.edu.wit.hairsalon.scheduledevent.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventTypeDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

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
