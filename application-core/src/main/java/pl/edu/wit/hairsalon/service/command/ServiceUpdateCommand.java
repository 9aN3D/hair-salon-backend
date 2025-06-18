package pl.edu.wit.hairsalon.service.command;

import java.math.BigDecimal;

public record ServiceUpdateCommand(
        String name,
        BigDecimal price,
        Long durationInMinutes
) {

}
