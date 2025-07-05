package pl.edu.wit.hairsalon.hairdresser.command;

import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

public record HairdresserDayOverrideUpdateCommand(TimeRangeDto customTimeRange) {

}
