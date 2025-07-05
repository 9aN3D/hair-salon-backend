package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideDto;
import pl.edu.wit.hairsalon.hairdresser.exception.HairdresserDayOverrideAlreadyExistsException;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

class HairdresserDayOverrideCreator {

    private final HairdresserDayOverridePort hairdresserDayOverridePort;

    HairdresserDayOverrideCreator(HairdresserDayOverridePort hairdresserDayOverridePort) {
        this.hairdresserDayOverridePort = hairdresserDayOverridePort;
    }

    HairdresserDayOverrideDto create(HairdresserDayOverrideCreateCommand command) {
        requireNonNull(command.id(), "HairdresserDayOverrideCreator, the command id must not be null");
        var newHairdresserDayOverride = buildHairdresserDayOverride(command).validate();
        if (hairdresserDayOverridePort.exists(newHairdresserDayOverride.id().toDto())) {
            throw new HairdresserDayOverrideAlreadyExistsException("HairdresserDayOverrideCreator, the id " + command.id() + " already exists");
        }
        return hairdresserDayOverridePort.save(newHairdresserDayOverride.toDto());
    }

    private HairdresserDayOverride buildHairdresserDayOverride(HairdresserDayOverrideCreateCommand command) {
        if (isNull(command.customTimeRange())) {
            return new ClosedOverride(command.id());
        }
        return new CustomHoursOverride(command.id(), command.customTimeRange());
    }

}
