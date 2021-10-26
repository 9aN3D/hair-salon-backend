package pl.edu.wit.hairdresser.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HairdresserCreateCommand extends HairdresserBaseCommand {

    @Builder
    public HairdresserCreateCommand(String name, String surname, Set<String> services) {
        super(name, surname, services);
    }

}
