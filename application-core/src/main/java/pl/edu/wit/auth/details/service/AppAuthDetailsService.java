package pl.edu.wit.auth.details.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.common.domain.Email;
import pl.edu.wit.common.domain.EncodedPassword;
import pl.edu.wit.auth.details.domain.AuthDetails;
import pl.edu.wit.auth.details.domain.AuthDetailsPassword;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.auth.details.exception.AuthDetailsAlreadyExists;
import pl.edu.wit.auth.details.exception.AuthDetailsNotFoundException;
import pl.edu.wit.auth.details.port.primary.AuthDetailsService;
import pl.edu.wit.auth.details.port.secondary.AuthDetailsDao;
import pl.edu.wit.common.port.secondary.IdGenerator;
import pl.edu.wit.common.port.secondary.PasswordEncoder;
import pl.edu.wit.auth.details.query.AuthDetailsFindQuery;

import static java.lang.String.format;
import static pl.edu.wit.auth.details.domain.AuthDetailsRole.MEMBER;
import static pl.edu.wit.auth.details.domain.AuthDetailsStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthDetailsService implements AuthDetailsService {

    private final AuthDetailsDao dao;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthDetailsDto save(String email, String password) {
        log.trace("Creating auth details: {email: {}}", email);
        throwIfExistByCommandEmail(email);
        var newAuthDetails = createNewAuthDetails(email, password);
        var savedAuthDetails = dao.save(newAuthDetails.toDto());
        log.trace("Created auth details: {email: {}}", savedAuthDetails);
        return savedAuthDetails;
    }

    @Override
    public AuthDetailsDto findOneById(String id) {
        log.trace("Getting auth details by id: {}", id);
        var authDetailsDto = getOneFromDaoOrThrow(id);
        log.info("Got auth details by id: {}", id);
        return authDetailsDto;
    }

    private void throwIfExistByCommandEmail(String email) {
        if (existByEmail(email)) {
            throw new AuthDetailsAlreadyExists(
                    format("Auth details already exists by email: %s", email));
        }
    }

    private Boolean existByEmail(String email) {
        return dao.findOne(AuthDetailsFindQuery.ofEmail(email)).isPresent();
    }

    private AuthDetails createNewAuthDetails(String email, String password) {
        return AuthDetails.builder()
                .id(idGenerator.generate())
                .password(
                        new EncodedPassword(
                                new AuthDetailsPassword(password),
                                passwordEncoder))
                .email(new Email(email))
                .status(ACTIVE)
                .role(MEMBER)
                .build();
    }

    private AuthDetailsDto getOneFromDaoOrThrow(String id) {
        return dao.findOne(AuthDetailsFindQuery.ofAuthDetailsId(id))
                .orElseThrow(() -> new AuthDetailsNotFoundException(
                        format("Auth details not found by id: %s", id)
                ));
    }

}
