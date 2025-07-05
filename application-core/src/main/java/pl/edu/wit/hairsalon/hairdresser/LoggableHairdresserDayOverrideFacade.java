package pl.edu.wit.hairsalon.hairdresser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

class LoggableHairdresserDayOverrideFacade implements HairdresserDayOverrideFacade {

    private final Logger log;
    private final HairdresserDayOverrideFacade delegate;

    LoggableHairdresserDayOverrideFacade(HairdresserDayOverrideFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableHairdresserDayOverrideFacade.class);
        this.delegate = delegate;
    }

    @Override
    public HairdresserDayOverrideIdDto create(HairdresserDayOverrideCreateCommand command) {
        log.trace("Creating hairdresser day override {command: {}}", command);
        var result = delegate.create(command);
        log.info("Created hairdresser day override {id: {}}", result);
        return result;
    }

    @Override
    public void update(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        log.trace("Updating hairdresser day override {id: {}, command: {}}", id, command);
        delegate.update(id, command);
        log.info("Updated hairdresser day override {id: {}}", id);
    }

}
