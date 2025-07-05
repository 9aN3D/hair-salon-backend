package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

import static java.util.Objects.isNull;

class HairdresserDayOverrideUpdater {

    private final HairdresserDayOverridePort hairdresserDayOverridePort;

    HairdresserDayOverrideUpdater(HairdresserDayOverridePort hairdresserDayOverridePort) {
        this.hairdresserDayOverridePort = hairdresserDayOverridePort;
    }

    void update(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        var hairdresserDayOverrideDto = hairdresserDayOverridePort.findOneOrThrow(id);
        var updatedHairdresserDayOverride = buildHairdresserDayOverride(hairdresserDayOverrideDto.id(), command).validate();
        hairdresserDayOverridePort.save(updatedHairdresserDayOverride.toDto());
    }

    private HairdresserDayOverride buildHairdresserDayOverride(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        if (isNull(command.customTimeRange())) {
            return new ClosedOverride(id);
        }
        return new CustomHoursOverride(id, command.customTimeRange());
    }

}
