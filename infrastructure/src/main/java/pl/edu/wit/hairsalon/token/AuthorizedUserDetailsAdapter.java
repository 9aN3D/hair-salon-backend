package pl.edu.wit.hairsalon.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsPort;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.ofEmail;

@Service
class AuthorizedUserDetailsAdapter implements UserDetailsService {

    private final Logger log;
    private final AuthDetailsPort authDetailsPort;
    private final IdGenerator idGenerator;

    public AuthorizedUserDetailsAdapter(AuthDetailsPort authDetailsPort, IdGenerator idGenerator) {
        this.log = LoggerFactory.getLogger(AuthorizedUserDetailsAdapter.class);
        this.authDetailsPort = authDetailsPort;
        this.idGenerator = idGenerator;
    }

    @Override
    public UserDetails loadUserByUsername(String idOrEmail) throws UsernameNotFoundException {
        log.info("Loading user by username {}", idOrEmail);
        return parseId(idOrEmail)
                .map(AuthDetailsFindQuery::withId)
                .map(authDetailsPort::findOne)
                .orElseGet(() -> authDetailsPort.findOne(ofEmail(idOrEmail)))
                .map(this::checkStatus)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("Cannot find user by id or email: {%s}", idOrEmail)));
    }

    private Optional<String> parseId(String idOrEmail) {
        return idGenerator.isValid(idOrEmail)
                ? of(idOrEmail)
                : empty();
    }

    private AuthDetailsDto checkStatus(AuthDetailsDto authDetailsDto) {
        return switch (authDetailsDto.status()) {
            case BANNED -> throw new UserBannedException("User is banned by administrator");
            case NOT_ACTIVE -> throw new UserNotActiveException("User is not active yet");
            default -> authDetailsDto;
        };
    }

    private UserDetails toUserDetails(AuthDetailsDto dto) {
        return User.builder()
                .username(dto.id())
                .password(dto.password())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities("ROLE_".concat(dto.role().toString()))
                .build();
    }

    static class UserBannedException extends AuthenticationException {

        public UserBannedException(String message) {
            super(message);
        }

    }

    static class UserNotActiveException extends AuthenticationException {

        public UserNotActiveException(String message) {
            super(message);
        }

    }

}
