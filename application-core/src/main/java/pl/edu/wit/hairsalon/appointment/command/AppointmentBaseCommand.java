package pl.edu.wit.hairsalon.appointment.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AppointmentBaseCommand {

    private String memberId;

}
