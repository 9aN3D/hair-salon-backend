package pl.edu.wit.hairsalon.service.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceUpdateCommand extends ServiceBaseCommand {

    public ServiceUpdateCommand(String name, BigDecimal price, Long durationInMinutes) {
        super(name, price, durationInMinutes);
    }

}
