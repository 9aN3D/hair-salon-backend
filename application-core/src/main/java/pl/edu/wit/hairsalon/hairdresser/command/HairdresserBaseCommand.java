package pl.edu.wit.hairsalon.hairdresser.command;

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
