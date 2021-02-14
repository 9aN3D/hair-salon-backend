package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.exception.auth_details.AuthDetailsNotFoundException;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.application.query.AuthDetailsFindQuery;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppAuthDetailsService implements AuthDetailsService {

    private final AuthDetailsDao dao;

    @Override
    public Boolean existByEmail(String email) {
        log.trace("Checking is exist auth details by email: {email: {}}", email);
        var isPresent = dao.findOne(AuthDetailsFindQuery.ofEmail(email)).isPresent();
        log.trace("Checked is exist auth details by email: {isPresent: {}}", isPresent);
        return isPresent;
    }

    @Override
    public AuthDetailsDto findOneById(String id) {
        log.trace("Getting auth details by id: {}", id);
        var authDetailsDto = dao.findOne(AuthDetailsFindQuery.ofAuthDetailsId(id))
                .map(AuthDetails::toDto)
                .orElseThrow(() -> new AuthDetailsNotFoundException(
                        format("Auth details not found by id: %s", id)
                ));
        log.info("Got auth details by id: {}", id);
        return authDetailsDto;
    }

}
