package pl.edu.wit.hairsalon.hairdresser;

import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

import static java.util.Objects.requireNonNull;

class AppHairdresserDayOverrideFacade implements HairdresserDayOverrideFacade {

    private final HairdresserDayOverrideCreator creator;
    private final HairdresserDayOverrideUpdater updater;

    AppHairdresserDayOverrideFacade(HairdresserDayOverrideCreator creator,
                                    HairdresserDayOverrideUpdater updater) {
        this.creator = creator;
        this.updater = updater;
    }

    @Override
    public HairdresserDayOverrideIdDto create(HairdresserDayOverrideCreateCommand command) {
        requireNonNull(command, "HairdresserDayOverride create command must not be null");
        var savedDto = creator.create(command);
        return savedDto.id();
    }

    @Override
    public void update(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        requireNonNull(id, "HairdresserDayOverride id must not be null");
        requireNonNull(command, "HairdresserDayOverride update command must not be null");
        updater.update(id, command);
    }

}
