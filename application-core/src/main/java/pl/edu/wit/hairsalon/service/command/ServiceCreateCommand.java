package pl.edu.wit.hairsalon.service.command;

import java.math.BigDecimal;

public record ServiceCreateCommand(
        String name,
        BigDecimal price,
        Long durationInMinutes
) {

}
