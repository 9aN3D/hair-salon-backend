package pl.edu.wit.application.command.hairdresser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HairdresserUpdateCommand extends HairdresserBaseCommand {

    private String photoId;

}
