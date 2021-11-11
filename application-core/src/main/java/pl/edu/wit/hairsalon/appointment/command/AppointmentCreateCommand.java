package pl.edu.wit.hairsalon.appointment.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AppointmentCreateCommand extends AppointmentBaseCommand {

    private String reservationId;

    private DateRangeDto times;

    private Set<String> serviceIds;

    private String hairdresserId;

    @Builder
    public AppointmentCreateCommand(String memberId) {
        super(memberId);
    }

}
