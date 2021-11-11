package pl.edu.wit.hairsalon.reservation.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ReservationMakeCommand {

    private String hairdresserId;

}
