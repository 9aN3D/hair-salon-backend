package pl.edu.wit.hairsalon.appointment.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AppointmentCreateCommand extends AppointmentBaseCommand {

    private String reservationId;

    private DateRangeDto times;

    private List<ServiceDto> services;

    private String hairdresserId;

    @Builder
    public AppointmentCreateCommand(String memberId,
                                    String reservationId,
                                    DateRangeDto times,
                                    List<ServiceDto> services,
                                    String hairdresserId) {
        super(memberId);
        this.reservationId = reservationId;
        this.times = times;
        this.services = services;
        this.hairdresserId = hairdresserId;
    }

}
