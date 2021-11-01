package pl.edu.wit.hairsalon.service.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceCreateCommand extends ServiceBaseCommand {

    public ServiceCreateCommand(String name, BigDecimal price, Long durationInMinutes) {
        super(name, price, durationInMinutes);
    }

}
