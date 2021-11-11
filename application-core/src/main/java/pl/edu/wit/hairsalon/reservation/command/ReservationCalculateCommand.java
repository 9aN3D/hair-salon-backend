package pl.edu.wit.hairsalon.reservation.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ReservationCalculateCommand {

    private String hairdresserId;

    private LocalDateTime startDateTime;

    private Set<String> selectedServiceIds;

}
