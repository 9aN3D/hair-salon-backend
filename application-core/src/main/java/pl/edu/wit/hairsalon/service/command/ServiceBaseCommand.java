package pl.edu.wit.hairsalon.service.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBaseCommand {

    private String name;

    private BigDecimal price;

    private Long durationInMinutes;

}
