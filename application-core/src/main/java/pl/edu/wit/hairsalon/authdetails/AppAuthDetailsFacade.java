package pl.edu.wit.hairsalon.authdetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.authdetails.query.AuthDetailsFindQuery.withId;

@Slf4j
@RequiredArgsConstructor
class AppAuthDetailsFacade implements AuthDetailsFacade {

    private final AuthDetailsPort authDetailsPort;
    private final AuthDetailsCreator creator;
    private final AuthDetailsUpdater updater;
    private final AuthDetailsRemover remover;

    @Override
    public AuthDetailsDto create(AuthDetailsCreateCommand command) {
        log.trace("Creating auth details: {command: {}}", command);
        requireNonNull(command, "Auth details create command must not be null");
        var authDetailsDto = creator.create(command);
        log.trace("Created auth details: {authDetailsDto: {}}", authDetailsDto);
        return authDetailsDto;
    }

    @Override
    public AuthDetailsDto update(String id, AuthDetailsUpdateCommand command) {
        log.trace("Updating auth details: {command: {}}", command);
        requireNonNull(id, "Auth details id must not be null");
        requireNonNull(command, "Auth details update command must not be null");
        var authDetailsDto = updater.update(id, command);
        log.info("Updated auth details: {authDetailsDto: {}}", authDetailsDto);
        return authDetailsDto;
    }

    @Override
    public AuthDetailsDto findOneById(String id) {
        log.trace("Getting auth details by id: {}", id);
        requireNonNull(id, "Auth details id must not be null");
        var authDetailsDto = authDetailsPort.findOneOrThrow(withId(id));
        log.info("Got auth details by id: {}", id);
        return authDetailsDto;
    }

    @Override
    public void remove(String id) {
        log.trace("Removing auth details by id: {}", id);
        requireNonNull(id, "Auth details id must not be null");
        remover.remove(id);
        log.info("Removed auth details by id: {}", id);
    }

}
