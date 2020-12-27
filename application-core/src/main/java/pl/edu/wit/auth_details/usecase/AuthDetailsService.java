package pl.edu.wit.auth_details.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.port.primary.CreateAuthDetailsUseCase;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;

@Slf4j
@RequiredArgsConstructor
public class AuthDetailsService implements CreateAuthDetailsUseCase {

    private final IdGenerator idGenerator;
    private final AuthDetailsRepository authDetailsRepository;

    @Override
    public String create(CreateAuthDetailsCommand command) {
        log.trace("Creating auth details: {command: {}}", command);
        var authDetails = new AuthDetails(idGenerator, command);
        var authDetailsId = authDetailsRepository.save(authDetails.toDto());
        log.info("Created auth details: {id: {}}", authDetailsId);
        return authDetailsId;
    }

}
