package pl.edu.wit.spring.adapter.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorizedUserDetailsService implements UserDetailsService {

    private final AuthDetailsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(this::checkStatus)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("Cannot find user by username: {%s}", username)));
    }

    private AuthDetailsDto checkStatus(AuthDetailsDto authDetailsDto) {
        switch (authDetailsDto.getAuthDetailsStatus()) {
            case BANNED:
                throw new UserBannedException("User is banned by administrator");
            case NOT_ACTIVE:
                throw new UserNotActiveException("User is not active yet");
            default:
                return authDetailsDto;
        }
    }

    private UserDetails toUserDetails(AuthDetailsDto authDetailsDto) {
        return User.builder()
                .username(authDetailsDto.getEmail())
                .password(authDetailsDto.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(authDetailsDto.getAuthDetailsRole().toString())
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
