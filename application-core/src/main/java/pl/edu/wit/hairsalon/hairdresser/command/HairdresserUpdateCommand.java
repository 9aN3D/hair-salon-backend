package pl.edu.wit.hairsalon.hairdresser.command;

import java.util.Set;

public record HairdresserUpdateCommand(
        String name,
        String surname,
        Set<String> services
) {

}
