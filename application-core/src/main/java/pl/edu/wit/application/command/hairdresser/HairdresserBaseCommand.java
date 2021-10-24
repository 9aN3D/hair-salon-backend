package pl.edu.wit.application.command.hairdresser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HairdresserBaseCommand {

    private String name;

    private String surname;

    private Set<String> services;

}
