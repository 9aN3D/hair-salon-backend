package pl.edu.wit.auth_details.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.PasswordEncoder;
import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.port.primary.CreateAuthDetails;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsAlreadyExists;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public final class AuthDetailsUseCase implements CreateAuthDetails {

    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailsRepository authDetailsRepository;

    @Override
    public String create(final CreateAuthDetailsCommand command) {
        log.trace("Creating auth details: {command: {}}", command);
        checkAuthDetailsExistsByEmail(command.getEmail());
        var authDetails = new AuthDetails(idGenerator, passwordEncoder, command);
        var authDetailsId = authDetailsRepository.save(authDetails);
        log.info("Created auth details: {id: {}}", authDetailsId);
        return authDetailsId;
    }

    private void checkAuthDetailsExistsByEmail(String email) {
        if (authDetailsRepository.findByEmail(email).isPresent()) {
            throw new AuthDetailsAlreadyExists(format("Auth details already exists by email: %s", email));
        }
    }

}
