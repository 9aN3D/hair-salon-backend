package pl.edu.wit.hairsalon.reservation.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ReservationMakeCommand {

    private String hairdresserId;

    private LocalDateTime startDateTime;

    private Set<String> selectedServiceIds;

    public ReservationCalculateCommand toCalculateCommand() {
        return ReservationCalculateCommand.builder()
                .hairdresserId(hairdresserId)
                .startDateTime(startDateTime)
                .selectedServiceIds(selectedServiceIds)
                .build();
    }

}
