package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.factory.AuthDetailsFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsRepository;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.exception.auth_details.AuthDetailsAlreadyExists;
import pl.edu.wit.domain.exception.auth_details.AuthDetailsNotFoundException;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppAuthDetailsService implements AuthDetailsService {

    private final AuthDetailsRepository repository;
    private final AuthDetailsFactory factory;

    @Override
    public String create(CreateAuthDetailsCommand command) {
        log.trace("Creating auth details: {command: {}}", command);
        checkAuthDetailsExistsByEmail(command.getEmail());
        var authDetails = factory.buildAuthDetails(command);
        var authDetailsId = repository.save(authDetails);
        log.info("Created auth details: {id: {}}", authDetailsId);
        return authDetailsId;
    }

    @Override
    public AuthDetailsDto findOneByEmail(String email) {
        log.trace("Getting auth details by email: {}", email);
        var authDetailsDto = repository.findByEmail(email)
                .orElseThrow(() -> new AuthDetailsNotFoundException(
                        format("Auth details not found by email: %s", email)
                ));
        log.info("Got auth details by email: {}", email);
        return authDetailsDto;
    }

    private void checkAuthDetailsExistsByEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new AuthDetailsAlreadyExists(format("Auth details already exists by email: %s", email));
        }
    }

}
