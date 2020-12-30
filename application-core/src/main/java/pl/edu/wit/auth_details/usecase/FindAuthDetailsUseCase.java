package pl.edu.wit.auth_details.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.auth_details.port.primary.FindAuthDetails;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class FindAuthDetailsUseCase implements FindAuthDetails {

    private final AuthDetailsRepository repository;

    @Override
    public Optional<AuthDetailsDto> findOneByEmail(String email) {
        log.trace("Searching auth details by email: {}", email);
        var authDetailsDto = repository.findByEmail(email);
        log.info("Searched auth details by email: {}", email);
        return authDetailsDto;
    }

}
