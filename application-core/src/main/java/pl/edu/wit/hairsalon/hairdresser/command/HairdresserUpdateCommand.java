package pl.edu.wit.hairsalon.hairdresser.command;

import pl.edu.wit.hairsalon.sharedKernel.dto.TimeRangeDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public record HairdresserUpdateCommand(
        String name,
        String surname,
        Set<String> services,
        Map<DayOfWeek, TimeRangeDto> weeklySchedule
) {

}
