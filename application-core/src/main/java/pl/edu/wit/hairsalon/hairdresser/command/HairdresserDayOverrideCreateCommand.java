package pl.edu.wit.hairsalon.hairdresser.command;

import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

public record HairdresserDayOverrideCreateCommand(
        HairdresserDayOverrideIdDto id,
        TimeRangeDto customTimeRange
) {

}
