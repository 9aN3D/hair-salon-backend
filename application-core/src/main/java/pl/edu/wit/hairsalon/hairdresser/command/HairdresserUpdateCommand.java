package pl.edu.wit.hairsalon.hairdresser.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HairdresserUpdateCommand extends HairdresserBaseCommand {

    HairdresserUpdateCommand(String name, String surname, Set<String> services) {
        super(name, surname, services);
    }

}
