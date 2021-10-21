package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.auth_details.AuthDetailsUpdateCommand;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.EncodedPassword;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.exception.auth_details.AuthDetailsAlreadyExists;
import pl.edu.wit.application.exception.auth_details.AuthDetailsNotFoundException;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.application.query.AuthDetailsFindQuery;

import static java.lang.String.format;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole.MEMBER;
import static pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus.ACTIVE;

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
