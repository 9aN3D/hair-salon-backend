package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.exception.auth_details.AuthDetailsNotFoundException;
import pl.edu.wit.domain.model.auth_details.AuthDetails;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppAuthDetailsService implements AuthDetailsService {

    private final AuthDetailsDao dao;

    @Override
    public Boolean existByEmail(String email) {
        log.trace("Checking is exist auth details by email: {email: {}}", email);
        var isPresent = dao.findOne(email).isPresent();
        log.trace("Checked is exist auth details by email: {isPresent: {}}", isPresent);
        return isPresent;
    }

    @Override
    public AuthDetailsDto findOneByEmail(String email) {
        log.trace("Getting auth details by email: {}", email);
        var authDetailsDto = dao.findOne(email)
                .map(AuthDetails::toDto)
                .orElseThrow(() -> new AuthDetailsNotFoundException(
                        format("Auth details not found by email: %s", email)
                ));
        log.info("Got auth details by email: {}", email);
        return authDetailsDto;
    }

}
