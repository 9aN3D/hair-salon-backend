package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.withId;

class AppAuthDetailsFacade implements AuthDetailsFacade {

    private final AuthDetailsPort authDetailsPort;
    private final AuthDetailsCreator creator;
    private final AuthDetailsUpdater updater;
    private final AuthDetailsRemover remover;

    AppAuthDetailsFacade(AuthDetailsPort authDetailsPort, AuthDetailsCreator creator,
                         AuthDetailsUpdater updater, AuthDetailsRemover remover) {
        this.authDetailsPort = authDetailsPort;
        this.creator = creator;
        this.updater = updater;
        this.remover = remover;
    }

    @Override
    public AuthDetailsDto create(AuthDetailsCreateCommand command) {
        requireNonNull(command, "Auth details create command must not be null");
        return creator.create(command);
    }

    @Override
    public AuthDetailsDto update(String id, AuthDetailsUpdateCommand command) {
        requireNonNull(id, "Auth details id must not be null");
        requireNonNull(command, "Auth details update command must not be null");
        return updater.update(id, command);
    }

    @Override
    public AuthDetailsDto findOneById(String id) {
        requireNonNull(id, "Auth details id must not be null");
        return authDetailsPort.findOneOrThrow(withId(id));
    }

    @Override
    public void remove(String id) {
        requireNonNull(id, "Auth details id must not be null");
        remover.remove(id);
    }

}
