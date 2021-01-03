package pl.edu.wit.auth_details;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.auth_details.port.primary.FindAuthDetailsUseCase;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsNotFoundException;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
final class DomainFindAuthDetailsUseCase implements FindAuthDetailsUseCase {

    private final AuthDetailsRepository repository;

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

}
