package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

public interface HairdresserDayOverrideFacade {

    HairdresserDayOverrideIdDto create(HairdresserDayOverrideCreateCommand command);

    void update(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command);

}
