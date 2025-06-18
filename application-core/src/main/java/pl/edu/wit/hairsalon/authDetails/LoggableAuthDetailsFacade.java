package pl.edu.wit.hairsalon.authDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

class LoggableAuthDetailsFacade implements AuthDetailsFacade {

    private final Logger log;
    private final AuthDetailsFacade delegate;

    LoggableAuthDetailsFacade(AuthDetailsFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableAuthDetailsFacade.class);
        this.delegate = delegate;
    }

    @Override
    public AuthDetailsDto create(AuthDetailsCreateCommand command) {
        log.trace("Creating auth details: {command: {}}", command);
        var result = delegate.create(command);
        log.trace("Created auth details: {authDetailsDto: {}}", result);
        return result;
    }

    @Override
    public AuthDetailsDto update(String id, AuthDetailsUpdateCommand command) {
        log.trace("Updating auth details: {id: {}, command: {}}", id, command);
        var result = delegate.update(id, command);
        log.info("Updated auth details: {authDetailsDto: {}}", result);
        return result;
    }

    @Override
    public AuthDetailsDto findOneById(String id) {
        log.trace("Getting auth details by id: {}", id);
        var result = delegate.findOneById(id);
        log.info("Got auth details by id: {}", id);
        return result;
    }

    @Override
    public void remove(String id) {
        log.trace("Removing auth details by id: {}", id);
        delegate.remove(id);
        log.info("Removed auth details by id: {}", id);
    }

}
